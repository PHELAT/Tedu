package com.phelat.tedu.daggercore

import android.app.Application
import com.phelat.tedu.dependencyinjection.ComponentBuilder
import com.phelat.tedu.dependencyinjection.DispatcherComponent
import kotlin.reflect.KClass
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

abstract class DaggerAndroidApplication : Application(), DispatcherContainer {

    protected val dispatchers = mutableMapOf<KClass<*>, ComponentBuilder<*>>()

    protected val startupTasks = LinkedHashMap<String, Runnable>()

    override fun componentInjector(dispatcherComponent: KClass<*>): ComponentBuilder<*> {
        return requireNotNull(dispatchers[dispatcherComponent])
    }

    protected fun runStartupTasks(logger: (String) -> Unit = {}) {
        startupTasks.forEach { entry ->
            logger.invoke("Running startup task: ${entry.key}")
            val duration = measureDuration(entry.value)
            logger.invoke("Finished startup task: ${entry.key} in: ${duration}ms")
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun measureDuration(code: Runnable): Double {
        val clock = TimeSource.Monotonic
        val mark = clock.markNow()
        code.run()
        return mark.elapsedNow().inMilliseconds
    }

    protected inline fun <reified T : DispatcherComponent> prepareFeature(
        componentBuilder: ComponentBuilder<T>
    ) {
        dispatchers += T::class to componentBuilder
    }

    protected inline fun <reified T : DispatcherComponent> installFeature(
        componentBuilder: ComponentBuilder<T>
    ) {
        componentBuilder.getComponent(startupTasks::putAll)
        dispatchers += T::class to componentBuilder
    }

}