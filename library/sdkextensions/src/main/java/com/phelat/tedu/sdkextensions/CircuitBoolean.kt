package com.phelat.tedu.sdkextensions

class CircuitBoolean(private val switches: Map<String, Boolean>) {

    val value: Boolean = switches.containsValue(false).not()

    fun switchOn(switch: String): CircuitBoolean {
        synchronized(LOCK) {
            return if (switches[switch] == false) {
                val newSwitches = switches.toMutableMap().apply {
                    put(switch, true)
                }
                CircuitBoolean(newSwitches)
            } else {
                this
            }
        }
    }

    fun switchOff(switch: String): CircuitBoolean {
        synchronized(LOCK) {
            return if (switches[switch] == true) {
                val newSwitches = switches.toMutableMap().apply {
                    put(switch, false)
                }
                CircuitBoolean(newSwitches)
            } else {
                this
            }
        }
    }

    companion object {
        private val LOCK = Any()
    }
}