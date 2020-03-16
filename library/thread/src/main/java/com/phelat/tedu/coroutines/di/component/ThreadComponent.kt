package com.phelat.tedu.coroutines.di.component

import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.coroutines.di.module.ThreadModule
import com.phelat.tedu.coroutines.di.scope.ThreadScope
import dagger.Component

@ThreadScope
@Component(modules = [ThreadModule::class])
interface ThreadComponent {

    fun exposeDispatcher(): Dispatcher
}