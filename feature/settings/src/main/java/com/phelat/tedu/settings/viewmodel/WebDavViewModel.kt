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
                    isSaveButtonEnabled.decrement()
                } else {
                    isSaveButtonEnabled.increment()
                }
            )
        }
    }

    fun onUsernameTextChange(username: String) {
        _viewStateObservable.update {
            copy(
                isSaveButtonEnabled = if (username.isEmpty()) {
                    isSaveButtonEnabled.decrement()
                } else {
                    isSaveButtonEnabled.increment()
                }
            )
        }
    }

    fun onPasswordTextChange(password: String) {
        _viewStateObservable.update {
            copy(
                isSaveButtonEnabled = if (password.isEmpty()) {
                    isSaveButtonEnabled.decrement()
                } else {
                    isSaveButtonEnabled.increment()
                }
            )
        }
    }
}