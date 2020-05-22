package com.phelat.tedu.sdkextensions

class CircuitBoolean(private val switches: Map<String, Boolean>) {

    val value: Boolean = switches.containsValue(false).not()

    fun switchOn(switch: String): CircuitBoolean {
        synchronized(LOCK) {
            val newSwitches = switches.toMutableMap().apply {
                put(switch, true)
            }
            return CircuitBoolean(newSwitches)
        }
    }

    fun switchOff(switch: String): CircuitBoolean {
        synchronized(LOCK) {
            val newSwitches = switches.toMutableMap().apply {
                put(switch, false)
            }
            return CircuitBoolean(newSwitches)
        }
    }

    companion object {
        private val LOCK = Any()
    }
}