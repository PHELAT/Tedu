package com.phelat.tedu.settings.di.component

import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.androiddagger.FeatureComponent
import com.phelat.tedu.androiddagger.FeatureModule
import com.phelat.tedu.dependencyinjection.feature.FeatureStartupComponent
import com.phelat.tedu.dependencyinjection.scope.FeatureScope
import com.phelat.tedu.settings.di.module.SettingsBindingModule
import com.phelat.tedu.settings.di.module.SettingsModule
import com.phelat.tedu.settings.di.module.SettingsStartUpModule
import com.phelat.tedu.settings.di.module.UserInterfaceModeModule
import dagger.Component

@FeatureScope
@Component(
    modules = [
        FeatureModule::class,
        SettingsStartUpModule::class,
        SettingsBindingModule::class,
        UserInterfaceModeModule::class,
        SettingsModule::class
    ],
    dependencies = [AndroidCoreComponent::class]
)
interface SettingsComponent : FeatureComponent, FeatureStartupComponent