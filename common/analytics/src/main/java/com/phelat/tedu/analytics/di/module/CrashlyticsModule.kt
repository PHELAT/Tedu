package com.phelat.tedu.analytics.di.module

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.phelat.tedu.dependencyinjection.common.CommonScope
import dagger.Module
import dagger.Provides

@Module
class CrashlyticsModule {

    @Provides
    @CommonScope
    fun provideCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }
}