package com.phelat.tedu.contributors.di.builder

import com.phelat.tedu.analytics.di.builder.AnalyticsComponentBuilder
import com.phelat.tedu.androidresource.di.builder.AndroidResourceComponentBuilder
import com.phelat.tedu.contributors.di.component.ContributorsComponent
import com.phelat.tedu.contributors.di.component.DaggerContributorsComponent
import com.phelat.tedu.coroutines.di.builder.ThreadComponentBuilder
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.StartupTasks
import com.phelat.tedu.networking.di.builder.NetworkingComponentBuilder

object ContributorsComponentBuilder : ComponentBuilder<ContributorsComponent>() {

    override fun initializeComponent(
        addStartupTask: (StartupTasks) -> Unit
    ): ContributorsComponent {
        return DaggerContributorsComponent.builder()
            .networkingComponent(NetworkingComponentBuilder.getComponent(addStartupTask))
            .threadComponent(ThreadComponentBuilder.getComponent(addStartupTask))
            .analyticsComponent(AnalyticsComponentBuilder.getComponent(addStartupTask))
            .androidResourceComponent(AndroidResourceComponentBuilder.getComponent(addStartupTask))
            .build()
    }
}