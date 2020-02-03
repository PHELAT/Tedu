package com.phelat.tedu.daggerandroid

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import kotlin.reflect.KClass

abstract class DaggerAndroidApplication : Application(), DispatcherContainer {

    protected val dispatchers = mutableMapOf<KClass<*>, DispatchingAndroidInjector<Any>>()

    override fun androidInjector(dispatcherComponent: KClass<*>): AndroidInjector<Any> {
        return requireNotNull(dispatchers[dispatcherComponent])
    }

}