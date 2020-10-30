package com.phelat.tedu.androiddagger

import com.phelat.tedu.dependencyinjection.ComponentBuilder
import kotlin.reflect.KClass

interface DispatcherContainer {

    fun androidInjector(dispatcherComponent: KClass<*>): ComponentBuilder<*>
}