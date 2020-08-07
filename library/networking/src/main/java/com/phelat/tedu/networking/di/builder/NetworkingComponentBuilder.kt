package com.phelat.tedu.networking.di.builder

import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.networking.di.component.DaggerNetworkingComponent
import com.phelat.tedu.networking.di.component.NetworkingComponent

object NetworkingComponentBuilder : ComponentBuilder<NetworkingComponent>() {

    override fun initializeComponent(addStartupTask: (StartupTasks) -> Unit): NetworkingComponent {
        return DaggerNetworkingComponent.create()
    }
}