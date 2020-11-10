package com.phelat.tedu.settings.datasource

import android.content.SharedPreferences
import android.os.Build
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.settings.entity.UserInterfaceMode
import javax.inject.Inject

class UserInterfaceModeDataSource @Inject constructor(
    private val settingsSharedPreferences: SharedPreferences
) : Readable<UserInterfaceMode>, Writable<UserInterfaceMode> {

    override fun read(): UserInterfaceMode {
        val defaultTheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            USER_INTERFACE_MODE_AUTOMATIC
        } else {
            USER_INTERFACE_MODE_LIGHT
        }
        return when (settingsSharedPreferences.getInt(USER_INTERFACE_MODE, defaultTheme)) {
            USER_INTERFACE_MODE_LIGHT -> UserInterfaceMode.LightMode
            USER_INTERFACE_MODE_DARK -> UserInterfaceMode.DarkMode
            USER_INTERFACE_MODE_AUTOMATIC -> UserInterfaceMode.Automatic
            else -> UserInterfaceMode.LightMode
        }
    }

    override fun write(input: UserInterfaceMode) {
        val userInterfaceMode = when (input) {
            UserInterfaceMode.LightMode -> USER_INTERFACE_MODE_LIGHT
            UserInterfaceMode.DarkMode -> USER_INTERFACE_MODE_DARK
            UserInterfaceMode.Automatic -> USER_INTERFACE_MODE_AUTOMATIC
        }
        settingsSharedPreferences.edit()
            .putInt(USER_INTERFACE_MODE, userInterfaceMode)
            .apply()
    }

    companion object {
        private const val USER_INTERFACE_MODE = "user_interface_mode"
        private const val USER_INTERFACE_MODE_LIGHT = -1
        private const val USER_INTERFACE_MODE_DARK = 0
        private const val USER_INTERFACE_MODE_AUTOMATIC = 1
    }
}