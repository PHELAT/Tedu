package com.phelat.tedu.androidresource.di.builder

import com.phelat.tedu.androidcore.di.builder.AndroidCoreComponentBuilder
import com.phelat.tedu.androidresource.di.component.AndroidResourceComponent
import com.phelat.tedu.androidresource.di.component.DaggerAndroidResourceComponent
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks

object AndroidResourceComponentBuilder : ComponentBuilder<AndroidResourceComponent>() {

    override fun initializeComponent(
        addStartupTask: (StartupTasks) -> Unit
    ): AndroidResourceComponent {
        return DaggerAndroidResourceComponent.builder()
            .androidCoreComponent(AndroidCoreComponentBuilder.getComponent(addStartupTask))
            .build()
    }
}