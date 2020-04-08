package com.phelat.tedu.settings.di.module

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.settings.di.qualifier.SettingsStartupTasks
import com.phelat.tedu.settings.entity.UserInterfaceMode
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.Multibinds
import dagger.multibindings.StringKey

@Module
interface SettingsStartUpModule {

    @Multibinds
    @SettingsStartupTasks
    fun bindStartupTasks(): StartupTasks

    companion object {
        @Provides
        @FeatureScope
        @IntoMap
        @StringKey("SetUserInterfaceModeTask")
        @SettingsStartupTasks
        fun provideUserInterfaceModeStartUpTask(readable: Readable<UserInterfaceMode>) = Runnable {
            UserInterfaceMode.currentUserInterfaceMode = readable.read()
        }
    }
}