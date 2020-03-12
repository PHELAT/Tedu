package com.phelat.tedu.coroutines.di.module

import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.dependencyinjection.ModuleContainer
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

object ThreadModule : ModuleContainer {

    override fun getModule() = module {
        single {
            Dispatcher(iO = Dispatchers.IO, main = Dispatchers.Main)
        }
    }
}