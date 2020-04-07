package com.phelat.tedu.dependencyinjection.feature

import dagger.Module
import dagger.multibindings.Multibinds

@Module
interface FeatureStartUpModule {

    @Multibinds
    @FeatureStartupTasks
    fun featureStringKeyedStartUpTasks(): MutableMap<String, Runnable>
}