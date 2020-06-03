package com.phelat.tedu.todo.entity

sealed class Action {

    abstract val actionName: String

    object Add : Action() {
        override val actionName: String = "add"
    }

    object Update : Action() {
        override val actionName: String = "update"
    }

    object Delete : Action() {
        override val actionName: String = "delete"
    }

    object Undefined : Action() {
        override val actionName: String = "undefined"
    }
}