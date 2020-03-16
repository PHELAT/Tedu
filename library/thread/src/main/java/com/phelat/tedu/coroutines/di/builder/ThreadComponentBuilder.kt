package com.phelat.tedu.coroutines.di.builder

import com.phelat.tedu.coroutines.di.component.DaggerThreadComponent
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.dependencyinjection.ComponentBuilder

object ThreadComponentBuilder : ComponentBuilder<ThreadComponent>() {

    override fun initializeComponent(): ThreadComponent {
        return DaggerThreadComponent.create()
    }
}