package com.phelat.tedu.date.di.builder

import com.phelat.tedu.date.di.component.DaggerDateComponent
import com.phelat.tedu.date.di.component.DateComponent
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks

object DateComponentBuilder : ComponentBuilder<DateComponent>() {

    override fun initializeComponent(addStartupTask: (StartupTasks) -> Unit): DateComponent {
        return DaggerDateComponent.create()
    }
}