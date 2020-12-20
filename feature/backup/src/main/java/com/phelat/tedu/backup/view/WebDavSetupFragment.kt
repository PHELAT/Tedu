package com.phelat.tedu.backup.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.phelat.tedu.androiddagger.inject
import com.phelat.tedu.backup.databinding.FragmentWebdavSetupBinding
import com.phelat.tedu.backup.di.component.BackupComponent
import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.backup.state.WebDavViewState
import com.phelat.tedu.backup.viewmodel.WebDavViewModel
import com.phelat.tedu.designsystem.component.view.ConfirmationBottomSheet
import com.phelat.tedu.designsystem.ext.makeLongSnackBar
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.phelat.tedu.navigation.observeNavigation
import com.phelat.tedu.plaugin.FragmentPlugin
import com.phelat.tedu.plaugin.PlauginFragment
import com.phelat.tedu.plugins.invoke
import com.phelat.tedu.plugins.plugin
import com.phelat.tedu.plugins.viewBinding
import com.phelat.tedu.sdkextensions.hideKeyboard
import javax.inject.Inject

class WebDavSetupFragment : PlauginFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val webDavViewModel: WebDavViewModel by viewModels { viewModelFactory }

    private var confirmationSheet: ConfirmationBottomSheet? = null

    private val viewBinding = viewBinding { inflater, container ->
        FragmentWebdavSetupBinding.inflate(inflater, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject<BackupComponent>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding()) {
            webDavUrlInput.addTextChangedListener(afterTextChanged = { editable ->
                webDavViewModel.onUrlTextChange(editable.toString())
            })
            webDavUsernameInput.addTextChangedListener(afterTextChanged = { editable ->
                webDavViewModel.onUsernameTextChange(editable.toString())
            })
            webDavPasswordInput.addTextChangedListener(afterTextChanged = { editable ->
                webDavViewModel.onPasswordTextChange(editable.toString())
            })
            saveSettings.setOnClickListener {
                webDavViewModel.onSaveCredentialsClick(
                    webDavUrlInput.text.toString(),
                    webDavUsernameInput.text.toString(),
                    webDavPasswordInput.text.toString()
                )
            }
            webDavDeleteConfig.setOnClickListener {
                webDavViewModel.onDeleteConfigClick()
            }
        }
        webDavViewModel.apply {
            viewStateObservable.observe(viewLifecycleOwner, ::updateState)
            credentialsObservable.observe(viewLifecycleOwner, ::updateCredentials)
            snackBarObservable.observe(viewLifecycleOwner, ::showSnackBar)
            navigationObservable.observeNavigation(this@WebDavSetupFragment)
            confirmationSheetObservable.observe(viewLifecycleOwner, ::observeConfirmation)
        }
    }

    private fun updateState(state: WebDavViewState) {
        with(viewBinding()) {
            saveSettings.apply {
                isEnabled = state.isSaveButtonEnabled.value
                isInvisible = state.isSaveButtonVisible.not()
            }
            saveSettingsProgress.isVisible = state.isSaveProgressVisible
            webDavUrlLayout.error = state.urlInputErrorMessage
            webDavUrlLayout.isErrorEnabled = state.isUrlInputErrorEnabled
            webDavDeleteConfig.isVisible = state.isDeleteConfigVisible
        }
    }

    private fun updateCredentials(credentials: WebDavCredentials) {
        with(viewBinding()) {
            webDavUrlInput.setText(credentials.url)
            webDavUsernameInput.setText(credentials.username)
            webDavPasswordInput.setText(credentials.password)
        }
    }

    private fun showSnackBar(message: String) {
        hideKeyboard(viewBinding().webDavPasswordInput.windowToken)
        requireActivity().makeLongSnackBar(message).show()
    }

    private fun observeConfirmation(message: String) {
        confirmationSheet?.show()
            ?: run {
                confirmationSheet = ConfirmationBottomSheet(requireContext()).apply {
                    sheetTitle = message
                    onOkayButtonClick = webDavViewModel::onOkayConfirmationClick
                }
                observeConfirmation(message)
            }
    }

    override fun onDestroyView() {
        if (confirmationSheet?.isShowing == true) {
            confirmationSheet?.dismiss()
        }
        super.onDestroyView()
    }

    override fun plugins(): MutableList<FragmentPlugin> = mutableListOf(viewBinding.plugin)
}