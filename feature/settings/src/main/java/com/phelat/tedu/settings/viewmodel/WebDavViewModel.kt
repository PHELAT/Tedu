package com.phelat.tedu.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phelat.tedu.lifecycle.update
import com.phelat.tedu.settings.state.WebDavViewState
import javax.inject.Inject

class WebDavViewModel @Inject constructor() : ViewModel() {

    private val _viewStateObservable = MutableLiveData(WebDavViewState())
    val viewStateObservable: LiveData<WebDavViewState> = _viewStateObservable

    fun onUrlTextChange(url: String) {
        _viewStateObservable.update {
            copy(
                isSaveButtonEnabled = if (url.isEmpty()) {
                    isSaveButtonEnabled.switchOff(URL_FIELD_SWITCH)
                } else {
                    isSaveButtonEnabled.switchOn(URL_FIELD_SWITCH)
                }
            )
        }
    }

    fun onUsernameTextChange(username: String) {
        _viewStateObservable.update {
            copy(
                isSaveButtonEnabled = if (username.isEmpty()) {
                    isSaveButtonEnabled.switchOff(USERNAME_FIELD_SWITCH)
                } else {
                    isSaveButtonEnabled.switchOn(USERNAME_FIELD_SWITCH)
                }
            )
        }
    }

    fun onPasswordTextChange(password: String) {
        _viewStateObservable.update {
            copy(
                isSaveButtonEnabled = if (password.isEmpty()) {
                    isSaveButtonEnabled.switchOff(PASSWORD_FIELD_SWITCH)
                } else {
                    isSaveButtonEnabled.switchOn(PASSWORD_FIELD_SWITCH)
                }
            )
        }
    }

    companion object {
        const val URL_FIELD_SWITCH = "url_field_switch"
        const val USERNAME_FIELD_SWITCH = "username_field_switch"
        const val PASSWORD_FIELD_SWITCH = "password_field_switch"
    }
}