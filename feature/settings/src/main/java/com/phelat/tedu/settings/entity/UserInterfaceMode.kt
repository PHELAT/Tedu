package com.phelat.tedu.settings.entity

sealed class UserInterfaceMode {

    object DarkMode : UserInterfaceMode()

    object LightMode : UserInterfaceMode()

    // TODO: Implement automatic mode
    object Automatic : UserInterfaceMode()

    companion object {
        var currentUserInterfaceMode: UserInterfaceMode = LightMode
    }
}