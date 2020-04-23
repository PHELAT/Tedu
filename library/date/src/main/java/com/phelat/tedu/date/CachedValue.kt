package com.phelat.tedu.date

fun <T : Any> cachedValue(block: () -> T): () -> T {
    val value = lazy {
        block.invoke()
    }
    return {
        value.value
    }
}