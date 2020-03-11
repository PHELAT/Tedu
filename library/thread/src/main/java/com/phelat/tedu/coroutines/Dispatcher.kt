package com.phelat.tedu.coroutines

import kotlinx.coroutines.CoroutineDispatcher

data class Dispatcher(
    val iO: CoroutineDispatcher,
    val main: CoroutineDispatcher
)
