package com.phelat.tedu.di.module

import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.dagger.ApplicationScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class TeduModule {

    @ApplicationScope
    @Provides
    fun provideDispatcher(): Dispatcher {
        return Dispatcher(iO = Dispatchers.IO, main = Dispatchers.Main)
    }
}