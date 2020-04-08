package com.phelat.tedu.coroutines.di.builder

import com.phelat.tedu.coroutines.di.component.DaggerThreadComponent
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks

object ThreadComponentBuilder : ComponentBuilder<ThreadComponent>() {

    override fun initializeComponent(addStartupTask: (StartupTasks) -> Unit): ThreadComponent {
        return DaggerThreadComponent.create()
    }
}