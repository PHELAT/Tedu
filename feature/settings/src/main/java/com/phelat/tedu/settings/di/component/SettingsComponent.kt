package com.phelat.tedu.settings.di.component

import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.androiddagger.DispatcherComponent
import com.phelat.tedu.androidresource.di.component.AndroidResourceComponent
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.settings.di.module.SettingsModule
import com.phelat.tedu.settings.di.module.SettingsStartUpModule
import com.phelat.tedu.settings.di.module.UserInterfaceModeModule
import com.phelat.tedu.settings.di.qualifier.SettingsStartupTasks
import com.phelat.tedu.sync.di.component.SyncComponent
import dagger.Component

@FeatureScope
@Component(
    modules = [
        SettingsStartUpModule::class,
        UserInterfaceModeModule::class,
        SettingsModule::class
    ],
    dependencies = [
        AndroidCoreComponent::class,
        AndroidResourceComponent::class,
        ThreadComponent::class,
        SyncComponent::class
    ]
)
interface SettingsComponent : DispatcherComponent {

    @SettingsStartupTasks
    fun getSettingsStartupTasks(): StartupTasks
}