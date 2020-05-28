package com.phelat.tedu.backup.di.module

import com.phelat.tedu.backup.datasource.WebDavCredentialsDataSource
import com.phelat.tedu.backup.datasource.WebDavDataSource
import com.phelat.tedu.backup.entity.BackupTodoEntity
import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.backup.error.WebDavErrorContext
import com.phelat.tedu.backup.repository.WebDavRepository
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.functional.Response
import dagger.Binds
import dagger.Module

@Module
internal interface WebDavBindingsModule {

    @Binds
    @CommonScope
    fun bindWritableWebDavCredentialsDataSource(
        input: WebDavCredentialsDataSource
    ): Writable<WebDavCredentials>

    @Binds
    @CommonScope
    fun bindReadableWebDavCredentialsDataSource(
        input: WebDavCredentialsDataSource
    ): Readable<WebDavCredentials>

    @Binds
    @CommonScope
    fun bindReadableWebDavDataSource(
        input: WebDavDataSource
    ): Readable.IO<WebDavCredentials, Response<BackupTodoEntity, WebDavErrorContext>>

    @Binds
    @CommonScope
    fun bindWebDavRepository(
        input: WebDavRepository
    ): Readable<Response<BackupTodoEntity, WebDavErrorContext>>
}