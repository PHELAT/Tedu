package com.phelat.tedu.settings.di.builder

import com.phelat.tedu.androidcore.di.builder.AndroidCoreComponentBuilder
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.settings.di.component.DaggerSettingsComponent
import com.phelat.tedu.settings.di.component.SettingsComponent

object SettingsComponentBuilder : ComponentBuilder<SettingsComponent>() {

    override fun initializeComponent(): SettingsComponent {
        return DaggerSettingsComponent.builder()
            .androidCoreComponent(AndroidCoreComponentBuilder.getComponent())
            .build()
    }
}