package com.phelat.tedu.coroutines.di.module

import com.phelat.tedu.coroutines.Dispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

object ThreadModule {

    fun getModule() = module(override = true) {
        single {
            Dispatcher(Dispatchers.IO, Dispatchers.Main)
        }
    }
}