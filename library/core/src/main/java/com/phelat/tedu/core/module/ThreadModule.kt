package com.phelat.tedu.core.module

import com.phelat.tedu.core.scope.CoreScope
import com.phelat.tedu.coroutines.Dispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class ThreadModule {

    @CoreScope
    @Provides
    fun provideDispatcher(): Dispatcher {
        return Dispatcher(iO = Dispatchers.IO, main = Dispatchers.Main)
    }
}