package com.phelat.tedu.coroutines.di.component

import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.coroutines.di.module.ThreadModule
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import dagger.Component

@LibraryScope
@Component(modules = [ThreadModule::class])
interface ThreadComponent {

    fun exposeDispatcher(): Dispatcher
}