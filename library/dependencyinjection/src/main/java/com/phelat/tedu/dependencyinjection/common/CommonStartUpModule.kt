package com.phelat.tedu.dependencyinjection.common

import dagger.Module
import dagger.multibindings.Multibinds

@Module
interface CommonStartUpModule {

    @Multibinds
    @CommonStartupTasks
    fun commonStringKeyedStartUpTasks(): MutableMap<String, Runnable>
}