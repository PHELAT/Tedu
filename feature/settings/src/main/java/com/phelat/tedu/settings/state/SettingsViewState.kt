package com.phelat.tedu.settings.state

data class SettingsViewState(
    val syncStateText: String = "",
    val isSyncStateTextVisible: Boolean = false,
    val isSyncArrowVisible: Boolean = true
)