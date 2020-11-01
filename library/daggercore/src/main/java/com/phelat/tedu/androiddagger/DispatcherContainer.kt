package com.phelat.tedu.androiddagger

import com.phelat.tedu.dependencyinjection.ComponentBuilder
import kotlin.reflect.KClass

interface DispatcherContainer {
    fun componentInjector(dispatcherComponent: KClass<*>): ComponentBuilder<*>
}