package com.phelat.tedu.analytics.di.module

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.phelat.tedu.analytics.BuildConfig
import com.phelat.tedu.analytics.di.qualifier.AnalyticsStartupTasks
import com.phelat.tedu.dependencyinjection.common.CommonScope
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
class AnalyticsStartupModule {

    @Provides
    @CommonScope
    @IntoMap
    @StringKey("CrashlyticsSwitchStartupTask")
    @AnalyticsStartupTasks
    fun provideCrashlyticsSwitchStartupTask(crashlytics: FirebaseCrashlytics) = Runnable {
        crashlytics.setCrashlyticsCollectionEnabled(BuildConfig.LOG_CRASHLYTICS)
    }
}