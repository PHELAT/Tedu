package com.phelat.tedu.core.expose

import android.content.Context
import com.phelat.tedu.core.qualifier.ApplicationContext
import com.phelat.tedu.coroutines.Dispatcher

interface CoreComponentExpose {

    @ApplicationContext
    fun applicationContext(): Context

    fun dispatcher(): Dispatcher
}