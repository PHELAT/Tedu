package com.phelat.tedu.settings.di.component

import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.dependencyinjection.DispatcherComponent
import com.phelat.tedu.androidresource.di.component.AndroidResourceComponent
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.settings.di.module.SettingsModule
import com.phelat.tedu.settings.di.module.SettingsStartUpModule
import com.phelat.tedu.settings.di.module.SettingsViewModelModule
import com.phelat.tedu.settings.di.module.UserInterfaceModeModule
import com.phelat.tedu.settings.di.qualifier.SettingsStartupTasks
import com.phelat.tedu.settings.di.scope.SettingsScope
import com.phelat.tedu.settings.view.SettingsFragment
import com.phelat.tedu.sync.di.component.SyncComponent
import dagger.Component

@SettingsScope
@Component(
    modules = [
        SettingsViewModelModule::class,
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

    fun inject(settingsFragment: SettingsFragment)
}