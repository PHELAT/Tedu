package com.phelat.tedu.androiddagger

import dagger.Module
import dagger.multibindings.Multibinds

@Module
interface StartUpModule {

    @Multibinds
    fun stringKeyedStartUpTasks(): MutableMap<String, Runnable>
}