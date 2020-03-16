package com.phelat.tedu.androiddagger

import dagger.android.AndroidInjector
import kotlin.reflect.KClass

interface DispatcherContainer {

    fun androidInjector(dispatcherComponent: KClass<*>): AndroidInjector<Any>
}