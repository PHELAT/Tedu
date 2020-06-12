package com.phelat.tedu.todo.error

sealed class TodoErrorContext : Throwable() {

    object InsertionFailed : TodoErrorContext()

    object UpdateFailed : TodoErrorContext()

    object DeletionFailed : TodoErrorContext()

    object ActionInsertionFailed : TodoErrorContext()

    object UndefinedAction : TodoErrorContext()
}