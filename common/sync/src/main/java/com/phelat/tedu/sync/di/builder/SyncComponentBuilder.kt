package com.phelat.tedu.sync.di.builder

import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.sync.di.component.DaggerSyncComponent
import com.phelat.tedu.sync.di.component.SyncComponent

object SyncComponentBuilder : ComponentBuilder<SyncComponent>() {

    override fun initializeComponent(addStartupTask: (StartupTasks) -> Unit): SyncComponent {
        return DaggerSyncComponent.create()
    }
}