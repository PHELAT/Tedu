package com.phelat.tedu.contributors.di.component

import com.phelat.tedu.analytics.di.component.AnalyticsComponent
import com.phelat.tedu.dependencyinjection.DispatcherComponent
import com.phelat.tedu.androidresource.di.component.AndroidResourceComponent
import com.phelat.tedu.contributors.di.module.ContributionsDataModule
import com.phelat.tedu.contributors.di.module.ContributorsNetworkModule
import com.phelat.tedu.contributors.di.module.ContributorsViewModelModule
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.view.ContributorsFragment
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.networking.di.component.NetworkingComponent
import dagger.Component

@ContributorsScope
@Component(
    modules = [
        ContributorsViewModelModule::class,
        ContributorsNetworkModule::class,
        ContributionsDataModule::class
    ],
    dependencies = [
        NetworkingComponent::class,
        ThreadComponent::class,
        AnalyticsComponent::class,
        AndroidResourceComponent::class
    ]
)
interface ContributorsComponent : DispatcherComponent {
    fun inject(contributorsFragment: ContributorsFragment)
}