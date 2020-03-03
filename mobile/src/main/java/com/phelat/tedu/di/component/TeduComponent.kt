package com.phelat.tedu.di.component

import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.dagger.ApplicationScope
import com.phelat.tedu.di.module.TeduModule
import dagger.Component

@ApplicationScope
@Component(modules = [TeduModule::class])
interface TeduComponent {

    fun dispatcher(): Dispatcher
}