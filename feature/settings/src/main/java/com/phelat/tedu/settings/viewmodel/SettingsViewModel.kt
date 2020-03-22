package com.phelat.tedu.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.lifecycle.SingleLiveData
import com.phelat.tedu.settings.entity.UserInterfaceMode
import com.phelat.tedu.uiview.Navigate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val uiModeDataSourceReadable: Readable<UserInterfaceMode>,
    private val uiModeDataSourceWritable: Writable<UserInterfaceMode>
) : ViewModel() {

    private val _darkModeSwitchObservable = SingleLiveData<Boolean>()
    val darkModeSwitchObservable: LiveData<Boolean> = _darkModeSwitchObservable

    private val _navigationObservable = SingleLiveData<Navigate>()
    val navigationObservable: LiveData<Navigate> = _navigationObservable

    init {
        UserInterfaceMode.currentUserInterfaceMode = uiModeDataSourceReadable.read()
        _darkModeSwitchObservable.value = UserInterfaceMode.currentUserInterfaceMode == UserInterfaceMode.DarkMode
    }

    fun onDarkModeSwitchChange(isSwitched: Boolean) {
        val userInterfaceMode = if (isSwitched) {
            UserInterfaceMode.DarkMode
        } else {
            UserInterfaceMode.LightMode
        }
        if (userInterfaceMode != uiModeDataSourceReadable.read()) {
            UserInterfaceMode.currentUserInterfaceMode = userInterfaceMode
            uiModeDataSourceWritable.write(userInterfaceMode)
            viewModelScope.launch {
                delay(DELAY_FOR_RECREATION_IN_MILLIS)
                _navigationObservable.value = Navigate.Recreate
            }
        }
    }

    companion object {
        private const val DELAY_FOR_RECREATION_IN_MILLIS = 200L
    }
}