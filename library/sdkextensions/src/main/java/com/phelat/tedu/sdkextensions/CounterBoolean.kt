package com.phelat.tedu.sdkextensions

class CounterBoolean(private val requiredCountToBeTrue: Int, private val counter: Int = 0) {

    val value: Boolean = requiredCountToBeTrue == counter

    fun increment(numberToIncrement: Int = 1): CounterBoolean {
        synchronized(LOCK) {
            return CounterBoolean(requiredCountToBeTrue, counter + numberToIncrement)
        }
    }

    fun decrement(numberToDecrement: Int = 1): CounterBoolean {
        synchronized(LOCK) {
            return CounterBoolean(requiredCountToBeTrue, counter - numberToDecrement)
        }
    }

    companion object {
        private val LOCK = Any()
    }
}