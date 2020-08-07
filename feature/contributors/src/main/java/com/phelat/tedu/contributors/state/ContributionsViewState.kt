package com.phelat.tedu.contributors.state

// TODO: use sealed class to model states
data class ContributionsViewState(
    val isProgressVisible: Boolean = false,
    val isErrorVisible: Boolean = false,
    val isListVisible: Boolean = false
)