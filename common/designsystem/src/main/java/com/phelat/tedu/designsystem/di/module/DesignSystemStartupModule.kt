package com.phelat.tedu.designsystem.di.module

import androidx.appcompat.app.AppCompatDelegate
import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.designsystem.di.qualifier.DesignSystemStartupTask
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
class DesignSystemStartupModule {

    @Provides
    @IntoMap
    @StringKey("EnableCompatVectorTask")
    @CommonScope
    @DesignSystemStartupTask
    fun provideEnableCompatVectorTask() = Runnable {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}