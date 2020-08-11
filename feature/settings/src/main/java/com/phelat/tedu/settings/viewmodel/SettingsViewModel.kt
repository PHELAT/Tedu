package com.phelat.tedu.settings.viewmodel

import android.os.Build
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.designsystem.entity.BottomSheetEntity
import com.phelat.tedu.designsystem.entity.BottomSheetItemEntity
import com.phelat.tedu.lifecycle.SingleLiveData
import com.phelat.tedu.navigation.Navigate
import com.phelat.tedu.settings.R
import com.phelat.tedu.settings.di.scope.SettingsScope
import com.phelat.tedu.settings.entity.UserInterfaceMode
import com.phelat.tedu.settings.state.SettingsViewState
import com.phelat.tedu.sync.state.SyncState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@SettingsScope
@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModel @Inject constructor(
    private val uiModeDataSourceReadable: Readable<UserInterfaceMode>,
    private val uiModeDataSourceWritable: Writable<UserInterfaceMode>,
    private val stringResourceProvider: ResourceProvider<StringId, StringResource>,
    private val syncStateReadable: Readable<Flow<SyncState>>,
    dispatcher: Dispatcher
) : ViewModel() {

    private val _userInterfaceTitleObservable = MutableLiveData<String>()
    val userInterfaceTitleObservable: LiveData<String> = _userInterfaceTitleObservable

    private val _navigationObservable = SingleLiveData<Navigate>()
    val navigationObservable: LiveData<Navigate> = _navigationObservable

    private val _userInterfaceSheetObservable = SingleLiveData<BottomSheetEntity>()
    val userInterfaceSheetObservable: LiveData<BottomSheetEntity> = _userInterfaceSheetObservable

    private val _backupMethodSheetObservable = SingleLiveData<BottomSheetEntity>()
    val backupMethodSheetObservable: LiveData<BottomSheetEntity> = _backupMethodSheetObservable

    val viewStateObservable: LiveData<SettingsViewState> = syncStateReadable.read()
        .map { state -> mapSyncStateToSettingsViewState(state) }
        .asLiveData(dispatcher.iO)

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
        val sheetItems = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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
        _userInterfaceSheetObservable.value = BottomSheetEntity(
            items = sheetItems,
            sheetTitle = getStringResource(R.string.settings_ui_mode_sheet_title)
        )
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
        return getStringResource(titleId)
    }

    fun onBackUpClick() {
        viewModelScope.launch {
            if (syncStateReadable.read().first() !is SyncState.NotConfigured) {
                onWebDavBackupMethodClick()
            } else {
                val sheetItems = listOf(
                    BottomSheetItemEntity(
                        itemIconResource = R.drawable.ic_backup_icon_secondary_24dp,
                        itemTitleResource = R.string.settings_backup_method_webdav_title,
                        itemOnClickListener = ::onWebDavBackupMethodClick
                    ),
                    BottomSheetItemEntity(
                        itemIconResource = R.drawable.ic_google_drive_icon_secondary_24dp,
                        itemTitleResource = R.string.settings_backup_method_drive_title,
                        itemOnClickListener = {}
                    )
                )
                _backupMethodSheetObservable.value = BottomSheetEntity(
                    items = sheetItems,
                    sheetTitle = getStringResource(R.string.settings_backup_method_title)
                )
            }
        }
    }

    private fun onWebDavBackupMethodClick() {
        viewModelScope.launch {
            delay(DELAY_FOR_NAVIGATING)
            val deepLinkId = StringId(R.string.deeplink_webdav_setup)
            stringResourceProvider.getResource(deepLinkId)
                .resource
                .run(Navigate::ToDeepLink)
                .also(_navigationObservable::setValue)
        }
    }

    private fun mapSyncStateToSettingsViewState(state: SyncState): SettingsViewState {
        return when (state) {
            is SyncState.Success -> {
                val syncStateText = getStringResource(R.string.sync_state_success)
                SettingsViewState(
                    syncStateText,
                    isSyncStateTextVisible = true,
                    isSyncArrowVisible = false
                )
            }
            is SyncState.Failure -> {
                val syncStateText = getStringResource(R.string.sync_state_failure)
                SettingsViewState(
                    syncStateText,
                    isSyncStateTextVisible = true,
                    isSyncArrowVisible = false
                )
            }
            is SyncState.Syncing -> {
                val syncStateText = getStringResource(R.string.sync_state_syncing)
                SettingsViewState(
                    syncStateText,
                    isSyncStateTextVisible = true,
                    isSyncArrowVisible = false
                )
            }
            is SyncState.NotConfigured -> {
                SettingsViewState(
                    syncStateText = "",
                    isSyncStateTextVisible = false,
                    isSyncArrowVisible = true
                )
            }
        }
    }

    private fun getStringResource(@StringRes id: Int): String {
        val stringId = StringId(id)
        return stringResourceProvider.getResource(stringId).resource
    }

    fun onContributorsClick() {
        viewModelScope.launch {
            delay(DELAY_FOR_NAVIGATING)
            val contributorsDeepLink = StringId(R.string.deeplink_contributors)
            stringResourceProvider.getResource(contributorsDeepLink)
                .resource
                .run(Navigate::ToDeepLink)
                .also(_navigationObservable::setValue)
        }
    }

    companion object {
        private const val DELAY_FOR_NAVIGATING = 200L
        private const val DELAY_FOR_RECREATION_IN_MILLIS = 200L
    }
}