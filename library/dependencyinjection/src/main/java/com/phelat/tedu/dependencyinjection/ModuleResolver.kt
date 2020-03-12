package com.phelat.tedu.dependencyinjection

import org.koin.core.KoinApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import java.util.LinkedList
import java.util.Queue

fun KoinApplication.installFeatures(vararg features: ModuleContainer) {
    val resolvedModules = resolveModules(*features)
    loadKoinModules(resolvedModules)
}

private fun resolveModules(vararg containers: ModuleContainer): List<Module> {
    val uniqueContainers = HashSet<ModuleContainer>()
    val listOfUniqueModules = mutableListOf<Module>()
    val containerStack: Queue<ModuleContainer> = LinkedList()
    containerStack.addAll(containers)
    while (containerStack.isNotEmpty()) {
        val container = containerStack.poll()
        val module = container.getModule()
        if (module.isLoaded.not() && uniqueContainers.contains(container).not()) {
            uniqueContainers.add(container)
            listOfUniqueModules += module
        }
        val dependencies = container.getModuleDependencies()
        if (dependencies.isNotEmpty()) {
            containerStack.addAll(dependencies)
        }
    }
    return listOfUniqueModules
}