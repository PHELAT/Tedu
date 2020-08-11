package com.phelat.tedu.designsystem.di.builder

import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.designsystem.di.component.DaggerDesignSystemComponent
import com.phelat.tedu.designsystem.di.component.DesignSystemComponent

object DesignSystemComponentBuilder : ComponentBuilder<DesignSystemComponent>() {

    override fun initializeComponent(
        addStartupTask: (StartupTasks) -> Unit
    ): DesignSystemComponent {
        return DaggerDesignSystemComponent.create()
    }

    override fun getStartupTasks(component: DesignSystemComponent): StartupTasks? {
        return component.startupTasks()
    }
}