package com.phelat.tedu.androiddagger

import android.app.Application
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.feature.FeatureStartupComponent
import com.phelat.tedu.dependencyinjection.feature.HasCommonDependency
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import kotlin.reflect.KClass
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

abstract class DaggerAndroidApplication : Application(), DispatcherContainer {

    protected val dispatchers = mutableMapOf<KClass<*>, DispatchingAndroidInjector<Any>>()

    protected val startupTasks = LinkedHashMap<String, Runnable>()

    override fun androidInjector(dispatcherComponent: KClass<*>): AndroidInjector<Any> {
        return requireNotNull(dispatchers[dispatcherComponent])
    }

    protected fun runStartupTasks() {
        startupTasks.forEach { entry ->
            if (BuildConfig.DEBUG) {
                println("Running startup task: ${entry.key}")
            }
            val duration = measureDuration(entry.value)
            if (BuildConfig.DEBUG) {
                println("Finished startup task: ${entry.key} in: ${duration}ms")
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun measureDuration(code: Runnable): Double {
        val clock = TimeSource.Monotonic
        val mark = clock.markNow()
        code.run()
        return mark.elapsedNow().inMilliseconds
    }

    protected inline fun <reified T : FeatureComponent> installFeature(
        componentBuilder: ComponentBuilder<T>
    ) {
        val component = componentBuilder.getComponent()
        dispatchers += T::class to component.dispatcher()
        if (component is HasCommonDependency) {
            startupTasks += component.commonStartUpTasks()
        }
        if (component is FeatureStartupComponent) {
            startupTasks += component.featureStartUpTasks()
        }
    }
}