package com.phelat.tedu.contributors.di.component

import com.phelat.tedu.androiddagger.DispatcherComponent
import com.phelat.tedu.contributors.di.module.ContributorsFragmentModule
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
    dependencies = [NetworkingComponent::class]
)
interface ContributorsComponent : DispatcherComponent