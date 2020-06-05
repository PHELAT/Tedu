package com.phelat.tedu.todo.error

sealed class TodoErrorContext {

    object InsertionFailed : TodoErrorContext()

    object UpdateFailed : TodoErrorContext()

    object DeletionFailed : TodoErrorContext()

    object ActionInsertionFailed : TodoErrorContext()
}