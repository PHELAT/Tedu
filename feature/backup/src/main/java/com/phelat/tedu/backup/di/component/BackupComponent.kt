package com.phelat.tedu.backup.di.component

import com.phelat.tedu.analytics.di.component.AnalyticsComponent
import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.androiddagger.DispatcherComponent
import com.phelat.tedu.androidresource.di.component.AndroidResourceComponent
import com.phelat.tedu.backup.di.module.BackupFragmentModule
import com.phelat.tedu.backup.di.module.BackupStartupTasksModule
import com.phelat.tedu.backup.di.module.WebDavBindingsModule
import com.phelat.tedu.backup.di.module.WebDavModule
import com.phelat.tedu.backup.di.qualifier.BackupStartupTasks
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.networking.di.component.NetworkingComponent
import com.phelat.tedu.sync.di.component.SyncComponent
import com.phelat.tedu.todo.di.component.TodoComponent
import dagger.Component
import dagger.android.AndroidInjectionModule

@FeatureScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        WebDavBindingsModule::class,
        WebDavModule::class,
        BackupStartupTasksModule::class,
        BackupFragmentModule::class
    ],
    dependencies = [
        AndroidCoreComponent::class,
        ThreadComponent::class,
        TodoComponent::class,
        AnalyticsComponent::class,
        AndroidResourceComponent::class,
        SyncComponent::class,
        NetworkingComponent::class
    ]
)
interface BackupComponent : DispatcherComponent {

    @BackupStartupTasks
    fun backupStartupTasks(): StartupTasks
}