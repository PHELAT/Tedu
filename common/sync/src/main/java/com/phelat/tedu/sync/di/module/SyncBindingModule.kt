package com.phelat.tedu.sync.di.module

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.sync.datasource.SyncStateDataSource
import com.phelat.tedu.sync.state.SyncState
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.flow.Flow

@Module
internal interface SyncBindingModule {

    @Binds
    fun bindSyncStateDataSourceReadable(input: SyncStateDataSource): Readable<Flow<SyncState>>

    @Binds
    fun bindSyncStateDataSourceWritable(input: SyncStateDataSource): Writable<SyncState>
}