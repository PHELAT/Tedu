package com.phelat.tedu.settings.viewmodel

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.designsystem.entity.BottomSheetItemEntity
import com.phelat.tedu.lifecycle.SingleLiveData
import com.phelat.tedu.settings.R
import com.phelat.tedu.settings.entity.UserInterfaceMode
import com.phelat.tedu.uiview.Navigate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val uiModeDataSourceReadable: Readable<UserInterfaceMode>,
    private val uiModeDataSourceWritable: Writable<UserInterfaceMode>,
    private val stringResourceProvider: ResourceProvider<StringId, StringResource>
) : ViewModel() {

    private val _userInterfaceTitleObservable = MutableLiveData<String>()
    val userInterfaceTitleObservable: LiveData<String> = _userInterfaceTitleObservable

    private val _navigationObservable = SingleLiveData<Navigate>()
    val navigationObservable: LiveData<Navigate> = _navigationObservable

    private val _userInterfaceSheetObservable = SingleLiveData<List<BottomSheetItemEntity>>()
    val userInterfaceSheetObservable: LiveData<List<BottomSheetItemEntity>> = _userInterfaceSheetObservable

    private val _backupMethodSheetObservable = SingleLiveData<List<BottomSheetItemEntity>>()
    val backupMethodSheetObservable: LiveData<List<BottomSheetItemEntity>> = _backupMethodSheetObservable

    init {
        _userInterfaceTitleObservable.value = getUserInterfaceModeTitle()
    }

    fun onChangeUserInterfaceModeClick() {
        val darkItem = BottomSheetItemEntity(
            itemIconResource = null,
            itemTitleResource = R.string.settings_ui_mode_dark,
            itemOnClickListener = {
                changeUserInterfaceMode(UserInterfaceMode.DarkMode)
            }
        )
        val lightItem = BottomSheetItemEntity(
            itemIconResource = null,
            itemTitleResource = R.string.settings_ui_mode_light,
            itemOnClickListener = {
                changeUserInterfaceMode(UserInterfaceMode.LightMode)
            }
        )
        _userInterfaceSheetObservable.value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            listOf(
                BottomSheetItemEntity(
                    itemIconResource = null,
                    itemTitleResource = R.string.settings_ui_mode_automatic,
                    itemOnClickListener = {
                        changeUserInterfaceMode(UserInterfaceMode.Automatic)
                    }
                ),
                darkItem,
                lightItem
            )
        } else {
            listOf(darkItem, lightItem)
        }
    }

    private fun changeUserInterfaceMode(userInterfaceMode: UserInterfaceMode) {
        if (userInterfaceMode != uiModeDataSourceReadable.read()) {
            UserInterfaceMode.currentUserInterfaceMode = userInterfaceMode
            uiModeDataSourceWritable.write(userInterfaceMode)
            _userInterfaceTitleObservable.value = getUserInterfaceModeTitle(userInterfaceMode)
            viewModelScope.launch {
                delay(DELAY_FOR_RECREATION_IN_MILLIS)
                _navigationObservable.value = Navigate.Recreate
            }
        }
    }

    private fun getUserInterfaceModeTitle(
        userInterfaceMode: UserInterfaceMode = uiModeDataSourceReadable.read()
    ): String {
        val titleId = when (userInterfaceMode) {
            is UserInterfaceMode.Automatic -> R.string.settings_ui_mode_automatic
            is UserInterfaceMode.DarkMode -> R.string.settings_ui_mode_dark
            is UserInterfaceMode.LightMode -> R.string.settings_ui_mode_light
        }
        return stringResourceProvider.getResource(StringId(titleId)).resource
    }

    fun onBackUpClick() {
        _backupMethodSheetObservable.value = listOf(
            BottomSheetItemEntity(
                itemIconResource = R.drawable.ic_backup_icon_secondary_24dp,
                itemTitleResource = R.string.backup_method_webdav_title,
                itemOnClickListener = {}
            ),
            BottomSheetItemEntity(
                itemIconResource = R.drawable.ic_google_drive_icon_secondary_24dp,
                itemTitleResource = R.string.backup_method_drive_title,
                itemOnClickListener = {}
            )
        )
    }

    companion object {
        private const val DELAY_FOR_RECREATION_IN_MILLIS = 200L
    }
}