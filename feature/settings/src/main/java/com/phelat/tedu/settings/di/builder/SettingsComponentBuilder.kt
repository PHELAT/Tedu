package com.phelat.tedu.settings.di.builder

import com.phelat.tedu.androidcore.di.builder.AndroidCoreComponentBuilder
import com.phelat.tedu.androidresource.di.builder.AndroidResourceComponentBuilder
import com.phelat.tedu.coroutines.di.builder.ThreadComponentBuilder
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.settings.di.component.DaggerSettingsComponent
import com.phelat.tedu.settings.di.component.SettingsComponent

object SettingsComponentBuilder : ComponentBuilder<SettingsComponent>() {

    override fun initializeComponent(addStartupTask: (StartupTasks) -> Unit): SettingsComponent {
        return DaggerSettingsComponent.builder()
            .androidCoreComponent(AndroidCoreComponentBuilder.getComponent(addStartupTask))
            .androidResourceComponent(AndroidResourceComponentBuilder.getComponent(addStartupTask))
            .threadComponent(ThreadComponentBuilder.getComponent(addStartupTask))
            .build()
    }

    override fun getStartupTasks(component: SettingsComponent): StartupTasks? {
        return component.getSettingsStartupTasks()
    }
}