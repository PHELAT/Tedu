package com.phelat.tedu.analytics.di.builder

import com.phelat.tedu.analytics.di.component.AnalyticsComponent
import com.phelat.tedu.analytics.di.component.DaggerAnalyticsComponent
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks

object AnalyticsComponentBuilder : ComponentBuilder<AnalyticsComponent>() {

    override fun initializeComponent(addStartupTask: (StartupTasks) -> Unit): AnalyticsComponent {
        return DaggerAnalyticsComponent.create()
    }

    override fun getStartupTasks(component: AnalyticsComponent): StartupTasks? {
        return component.getAnalyticsStartupTasks()
    }
}