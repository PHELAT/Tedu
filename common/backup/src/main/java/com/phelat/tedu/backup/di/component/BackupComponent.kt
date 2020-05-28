package com.phelat.tedu.backup.di.component

import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.backup.di.module.BackupStartupTasksModule
import com.phelat.tedu.backup.di.module.WebDavBindingsModule
import com.phelat.tedu.backup.di.module.WebDavModule
import com.phelat.tedu.backup.di.qualifier.BackupStartupTasks
import com.phelat.tedu.backup.entity.WebDavCredentials
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.todo.di.component.TodoComponent
import dagger.Component

@CommonScope
@Component(
    modules = [WebDavBindingsModule::class, WebDavModule::class, BackupStartupTasksModule::class],
    dependencies = [AndroidCoreComponent::class, ThreadComponent::class, TodoComponent::class]
)
interface BackupComponent {

    @BackupStartupTasks
    fun backupStartupTasks(): StartupTasks

    fun exposeWebDavCredentialsReadable(): Readable<WebDavCredentials>

    fun exposeWebDavCredentialsWritable(): Writable<WebDavCredentials>
}