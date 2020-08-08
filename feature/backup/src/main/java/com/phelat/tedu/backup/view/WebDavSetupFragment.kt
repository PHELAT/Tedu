package com.phelat.tedu.backup.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.phelat.tedu.androiddagger.inject
import com.phelat.tedu.backup.R
import com.phelat.tedu.backup.di.component.BackupComponent
import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.backup.state.WebDavViewState
import com.phelat.tedu.backup.viewmodel.WebDavViewModel
import com.phelat.tedu.designsystem.component.view.ConfirmationBottomSheet
import com.phelat.tedu.designsystem.ext.makeLongSnackBar
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.phelat.tedu.navigation.observeNavigation
import com.phelat.tedu.sdkextensions.hideKeyboard
import kotlinx.android.synthetic.main.fragment_webdav_setup.saveSettings
import kotlinx.android.synthetic.main.fragment_webdav_setup.saveSettingsProgress
import kotlinx.android.synthetic.main.fragment_webdav_setup.webDavDeleteConfig
import kotlinx.android.synthetic.main.fragment_webdav_setup.webDavPasswordInput
import kotlinx.android.synthetic.main.fragment_webdav_setup.webDavUrlInput
import kotlinx.android.synthetic.main.fragment_webdav_setup.webDavUrlLayout
import kotlinx.android.synthetic.main.fragment_webdav_setup.webDavUsernameInput
import javax.inject.Inject

class WebDavSetupFragment : Fragment(R.layout.fragment_webdav_setup) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val webDavViewModel: WebDavViewModel by viewModels { viewModelFactory }

    private var confirmationSheet: ConfirmationBottomSheet? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject<BackupComponent>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        webDavViewModel.apply {
            viewStateObservable.observe(viewLifecycleOwner, ::updateState)
            credentialsObservable.observe(viewLifecycleOwner, ::updateCredentials)
            snackBarObservable.observe(viewLifecycleOwner, ::showSnackBar)
            navigationObservable.observeNavigation(this@WebDavSetupFragment)
            confirmationSheetObservable.observe(viewLifecycleOwner, ::observeConfirmation)
        }
    }

    private fun updateState(state: WebDavViewState) {
        saveSettings.apply {
            isEnabled = state.isSaveButtonEnabled.value
            isInvisible = state.isSaveButtonVisible.not()
        }
        saveSettingsProgress.isVisible = state.isSaveProgressVisible
        webDavUrlLayout.error = state.urlInputErrorMessage
        webDavUrlLayout.isErrorEnabled = state.isUrlInputErrorEnabled
        webDavDeleteConfig.isVisible = state.isDeleteConfigVisible
    }

    private fun updateCredentials(credentials: WebDavCredentials) {
        webDavUrlInput.setText(credentials.url)
        webDavUsernameInput.setText(credentials.username)
        webDavPasswordInput.setText(credentials.password)
    }

    private fun showSnackBar(message: String) {
        hideKeyboard(webDavPasswordInput.windowToken)
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
}