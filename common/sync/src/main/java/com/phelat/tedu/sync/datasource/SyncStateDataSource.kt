package com.phelat.tedu.sync.datasource

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.sync.state.SyncState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

@CommonScope
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
internal class SyncStateDataSource @Inject constructor() :
    Readable<@JvmSuppressWildcards Flow<SyncState>>,
    Writable<SyncState> {

    private val channel = ConflatedBroadcastChannel<SyncState>()

    override fun read(): Flow<SyncState> {
        return channel.asFlow()
    }

    override fun write(input: SyncState) {
        channel.offer(input)
    }
}