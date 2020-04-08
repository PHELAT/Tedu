package com.phelat.tedu.settings.di.component

import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.androiddagger.DispatcherComponent
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.settings.di.module.SettingsBindingModule
import com.phelat.tedu.settings.di.module.SettingsModule
import com.phelat.tedu.settings.di.module.SettingsStartUpModule
import com.phelat.tedu.settings.di.module.UserInterfaceModeModule
import com.phelat.tedu.settings.di.qualifier.SettingsStartupTasks
import dagger.Component
import dagger.android.AndroidInjectionModule

@FeatureScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        SettingsStartUpModule::class,
        SettingsBindingModule::class,
        UserInterfaceModeModule::class,
        SettingsModule::class
    ],
    dependencies = [AndroidCoreComponent::class]
)
interface SettingsComponent : DispatcherComponent {

    @SettingsStartupTasks
    fun getSettingsStartupTasks(): StartupTasks
}