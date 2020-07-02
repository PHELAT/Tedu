package com.phelat.tedu.sync.di.component

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.sync.di.module.SyncBindingModule
import com.phelat.tedu.sync.state.SyncState
import dagger.Component
import kotlinx.coroutines.flow.Flow

@CommonScope
@Component(modules = [SyncBindingModule::class])
interface SyncComponent {

    fun exposeSyncDataSourceReadable(): Readable<Flow<SyncState>>

    fun exposeSyncDataSourceWritable(): Writable<SyncState>
}