package com.phelat.tedu.backup.di.component

import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.backup.di.module.WebDavBindingsModule
import com.phelat.tedu.backup.di.module.WebDavModule
import com.phelat.tedu.backup.entity.BackupTodoEntity
import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.backup.error.WebDavErrorContext
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.functional.Response
import dagger.Component

@CommonScope
@Component(
    modules = [WebDavBindingsModule::class, WebDavModule::class],
    dependencies = [AndroidCoreComponent::class]
)
interface BackupComponent {

    fun exposeWebDavCredentialsReadable(): Readable<WebDavCredentials>

    fun exposeWebDavCredentialsWritable(): Writable<WebDavCredentials>

    fun exposeWebDavRepositoryReadable(): Readable<Response<BackupTodoEntity, WebDavErrorContext>>
}