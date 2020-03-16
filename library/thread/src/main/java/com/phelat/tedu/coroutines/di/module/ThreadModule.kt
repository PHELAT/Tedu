package com.phelat.tedu.coroutines.di.module

import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.coroutines.di.scope.ThreadScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class ThreadModule {

    @Provides
    @ThreadScope
    fun provideDispatcher(): Dispatcher {
        return Dispatcher(iO = Dispatchers.IO, main = Dispatchers.Main)
    }
}