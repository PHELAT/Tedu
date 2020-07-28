package com.phelat.tedu.contributors.di.builder

import com.phelat.tedu.contributors.di.component.ContributorsComponent
import com.phelat.tedu.contributors.di.component.DaggerContributorsComponent
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks

object ContributorsComponentBuilder : ComponentBuilder<ContributorsComponent>() {

    override fun initializeComponent(addStartupTask: (StartupTasks) -> Unit): ContributorsComponent {
        return DaggerContributorsComponent.create()
    }
}