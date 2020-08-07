package com.phelat.tedu.backup.di.builder

import com.phelat.tedu.analytics.di.builder.AnalyticsComponentBuilder
import com.phelat.tedu.androidcore.di.builder.AndroidCoreComponentBuilder
import com.phelat.tedu.androidresource.di.builder.AndroidResourceComponentBuilder
import com.phelat.tedu.backup.di.component.BackupComponent
import com.phelat.tedu.backup.di.component.DaggerBackupComponent
import com.phelat.tedu.coroutines.di.builder.ThreadComponentBuilder
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.networking.di.builder.NetworkingComponentBuilder
import com.phelat.tedu.sync.di.builder.SyncComponentBuilder
import com.phelat.tedu.todo.di.builder.TodoComponentBuilder

object BackupComponentBuilder : ComponentBuilder<BackupComponent>() {

    override fun initializeComponent(addStartupTask: (StartupTasks) -> Unit): BackupComponent {
        return DaggerBackupComponent.builder()
            .androidCoreComponent(AndroidCoreComponentBuilder.getComponent(addStartupTask))
            .threadComponent(ThreadComponentBuilder.getComponent(addStartupTask))
            .todoComponent(TodoComponentBuilder.getComponent(addStartupTask))
            .analyticsComponent(AnalyticsComponentBuilder.getComponent(addStartupTask))
            .androidResourceComponent(AndroidResourceComponentBuilder.getComponent(addStartupTask))
            .syncComponent(SyncComponentBuilder.getComponent(addStartupTask))
            .networkingComponent(NetworkingComponentBuilder.getComponent(addStartupTask))
            .build()
    }

    override fun getStartupTasks(component: BackupComponent): StartupTasks? {
        return component.backupStartupTasks()
    }
}