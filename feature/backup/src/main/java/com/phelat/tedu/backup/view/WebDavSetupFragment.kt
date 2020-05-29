package com.phelat.tedu.backup.view

import android.content.Context
import android.os.Bundle
import android.view.View
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
import com.phelat.tedu.lifecycle.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_webdav_setup.saveSettings
import kotlinx.android.synthetic.main.fragment_webdav_setup.webDavPasswordInput
import kotlinx.android.synthetic.main.fragment_webdav_setup.webDavUrlInput
import kotlinx.android.synthetic.main.fragment_webdav_setup.webDavUsernameInput
import javax.inject.Inject

class WebDavSetupFragment : Fragment(R.layout.fragment_webdav_setup) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val webDavViewModel: WebDavViewModel by viewModels { viewModelFactory }

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
        webDavViewModel.apply {
            viewStateObservable.observe(viewLifecycleOwner, ::updateState)
            credentialsObservable.observe(viewLifecycleOwner, ::updateCredentials)
        }
    }

    private fun updateState(state: WebDavViewState) {
        saveSettings.isEnabled = state.isSaveButtonEnabled.value
    }

    private fun updateCredentials(credentials: WebDavCredentials) {
        webDavUrlInput.setText(credentials.url)
        webDavUsernameInput.setText(credentials.username)
        webDavPasswordInput.setText(credentials.password)
    }
}