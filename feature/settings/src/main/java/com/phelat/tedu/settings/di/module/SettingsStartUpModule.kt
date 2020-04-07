package com.phelat.tedu.settings.di.module

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.dependencyinjection.feature.FeatureStartupTasks
import com.phelat.tedu.dependencyinjection.scope.FeatureScope
import com.phelat.tedu.settings.entity.UserInterfaceMode
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
class SettingsStartUpModule {

    @Provides
    @FeatureScope
    @IntoMap
    @StringKey("SetUserInterfaceModeTask")
    @FeatureStartupTasks
    fun provideUserInterfaceModeStartUpTask(readable: Readable<UserInterfaceMode>) = Runnable {
        UserInterfaceMode.currentUserInterfaceMode = readable.read()
    }
}