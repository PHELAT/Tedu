package com.phelat.tedu.daggerandroid

import dagger.android.AndroidInjector
import kotlin.reflect.KClass

internal interface DispatcherContainer {

    fun androidInjector(dispatcherComponent: KClass<*>): AndroidInjector<Any>
}