package com.phelat.tedu.backup.di.builder

import com.phelat.tedu.androidcore.di.builder.AndroidCoreComponentBuilder
import com.phelat.tedu.backup.di.component.BackupComponent
import com.phelat.tedu.backup.di.component.DaggerBackupComponent
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks

object BackupComponentBuilder : ComponentBuilder<BackupComponent>() {

    override fun initializeComponent(addStartupTask: (StartupTasks) -> Unit): BackupComponent {
        return DaggerBackupComponent.builder()
            .androidCoreComponent(AndroidCoreComponentBuilder.getComponent(addStartupTask))
            .build()
    }
}