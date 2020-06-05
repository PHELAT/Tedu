package com.phelat.tedu.backup.di.module

import com.phelat.tedu.backup.BackupSyncRepository
import com.phelat.tedu.backup.datasource.WebDavCredentialsDataSource
import com.phelat.tedu.backup.datasource.WebDavDataSource
import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.backup.error.BackupErrorContext
import com.phelat.tedu.backup.repository.WebDavSyncRepository
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.functional.Response
import com.phelat.tedu.todo.entity.ActionEntity
import dagger.Binds
import dagger.Module

@Module
internal interface WebDavBindingsModule {

    @Binds
    @FeatureScope
    fun bindWritableWebDavCredentialsDataSource(
        input: WebDavCredentialsDataSource
    ): Writable<WebDavCredentials>

    @Binds
    @FeatureScope
    fun bindReadableWebDavCredentialsDataSource(
        input: WebDavCredentialsDataSource
    ): Readable<Response<WebDavCredentials, BackupErrorContext>>

    @Binds
    @FeatureScope
    fun bindReadableWebDavDataSource(
        input: WebDavDataSource
    ): Readable.IO<WebDavCredentials, Response<List<ActionEntity>, BackupErrorContext>>

    @Binds
    @FeatureScope
    fun bindWebDavRepository(
        input: WebDavSyncRepository
    ): BackupSyncRepository
}