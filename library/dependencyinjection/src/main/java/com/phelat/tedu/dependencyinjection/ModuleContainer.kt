package com.phelat.tedu.dependencyinjection

import org.koin.core.module.Module

interface ModuleContainer {

    fun getModuleDependencies(): List<ModuleContainer> = emptyList()

    fun getModule(): Module
}