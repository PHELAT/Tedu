package com.phelat.tedu.settings.entity

sealed class UserInterfaceMode {

    object DarkMode : UserInterfaceMode()

    object LightMode : UserInterfaceMode()

    object Automatic : UserInterfaceMode()

    companion object {
        var currentUserInterfaceMode: UserInterfaceMode = LightMode
    }
}