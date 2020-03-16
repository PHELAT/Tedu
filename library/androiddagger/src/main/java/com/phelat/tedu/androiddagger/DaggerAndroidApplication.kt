package com.phelat.tedu.androiddagger

import android.app.Application
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import kotlin.reflect.KClass

abstract class DaggerAndroidApplication : Application(), DispatcherContainer {

    protected val dispatchers = mutableMapOf<KClass<*>, DispatchingAndroidInjector<Any>>()

    override fun androidInjector(dispatcherComponent: KClass<*>): AndroidInjector<Any> {
        return requireNotNull(dispatchers[dispatcherComponent])
    }

    protected inline fun <reified T : DispatcherComponent> installFeature(
        component: ComponentBuilder<T>
    ) {
        dispatchers += T::class to component.getComponent().dispatcher()
    }
}