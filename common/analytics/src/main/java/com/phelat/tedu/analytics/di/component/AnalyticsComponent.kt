package com.phelat.tedu.analytics.di.component

import com.phelat.tedu.analytics.ExceptionLogger
import com.phelat.tedu.analytics.di.module.AnalyticsStartupModule
import com.phelat.tedu.analytics.di.module.CrashlyticsModule
import com.phelat.tedu.analytics.di.module.LoggerModule
import com.phelat.tedu.analytics.di.qualifier.AnalyticsStartupTasks
import com.phelat.tedu.analytics.di.qualifier.Development
import com.phelat.tedu.analytics.di.qualifier.NonFatal
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.dependencyinjection.common.CommonScope
import dagger.Component

@CommonScope
@Component(modules = [AnalyticsStartupModule::class, CrashlyticsModule::class, LoggerModule::class])
interface AnalyticsComponent {

    @NonFatal
    fun exposeNonFatalExceptionLogger(): ExceptionLogger

    @Development
    fun exposeDevelopmentExceptionLogger(): ExceptionLogger

    @AnalyticsStartupTasks
    fun getAnalyticsStartupTasks(): StartupTasks
}