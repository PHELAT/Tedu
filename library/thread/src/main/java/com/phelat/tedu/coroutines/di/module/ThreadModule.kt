package com.phelat.tedu.coroutines.di.module

import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class ThreadModule {

    @Provides
    @LibraryScope
    fun provideDispatcher(): Dispatcher {
        return Dispatcher(iO = Dispatchers.IO, main = Dispatchers.Main)
    }
}