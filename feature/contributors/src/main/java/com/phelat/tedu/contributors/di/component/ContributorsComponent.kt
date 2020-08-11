package com.phelat.tedu.contributors.di.component

import com.phelat.tedu.analytics.di.component.AnalyticsComponent
import com.phelat.tedu.androiddagger.DispatcherComponent
import com.phelat.tedu.androidresource.di.component.AndroidResourceComponent
import com.phelat.tedu.contributors.di.module.ContributorsFragmentModule
import com.phelat.tedu.coroutines.di.component.ThreadComponent
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import com.phelat.tedu.networking.di.component.NetworkingComponent
import dagger.Component
import dagger.android.AndroidInjectionModule

@FeatureScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        ContributorsFragmentModule::class
    ],
    dependencies = [
        NetworkingComponent::class,
        ThreadComponent::class,
        AnalyticsComponent::class,
        AndroidResourceComponent::class
    ]
)
interface ContributorsComponent : DispatcherComponent