package com.phelat.tedu.analytics.di.component

import com.phelat.tedu.analytics.di.module.AnalyticsStartupModule
import com.phelat.tedu.analytics.di.qualifier.AnalyticsStartupTasks
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.dependencyinjection.common.CommonScope
import dagger.Component

@CommonScope
@Component(modules = [AnalyticsStartupModule::class])
interface AnalyticsComponent {

    @AnalyticsStartupTasks
    fun getAnalyticsStartupTasks(): StartupTasks
}