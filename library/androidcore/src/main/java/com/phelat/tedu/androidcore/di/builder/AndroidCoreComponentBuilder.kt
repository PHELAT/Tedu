package com.phelat.tedu.androidcore.di.builder

import android.app.Application
import com.phelat.tedu.androidcore.di.component.AndroidCoreComponent
import com.phelat.tedu.androidcore.di.component.DaggerAndroidCoreComponent
import com.phelat.tedu.dependencyinjection.ComponentBuilder

object AndroidCoreComponentBuilder : ComponentBuilder<AndroidCoreComponent>() {

    lateinit var application: Application

    override fun initializeComponent(): AndroidCoreComponent {
        return DaggerAndroidCoreComponent.builder()
            .bindApplication(application)
            .build()
    }
}