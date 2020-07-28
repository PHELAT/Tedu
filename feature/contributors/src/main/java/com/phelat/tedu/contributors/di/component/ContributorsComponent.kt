package com.phelat.tedu.contributors.di.component

import com.phelat.tedu.androiddagger.DispatcherComponent
import com.phelat.tedu.contributors.di.module.ContributorsFragmentModule
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import dagger.Component
import dagger.android.AndroidInjectionModule

@FeatureScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        ContributorsFragmentModule::class
    ]
)
interface ContributorsComponent : DispatcherComponent