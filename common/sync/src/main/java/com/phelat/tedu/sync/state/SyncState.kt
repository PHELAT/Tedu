package com.phelat.tedu.sync.state

sealed class SyncState {

    object Success : SyncState()

    object Failure : SyncState()

    object Syncing : SyncState()

    object NotConfigured : SyncState()
}