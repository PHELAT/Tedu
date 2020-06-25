package com.phelat.tedu.todo.error

sealed class TodoErrorContext : Throwable() {

    class InsertionFailed : TodoErrorContext()

    class UpdateFailed : TodoErrorContext()

    class DeletionFailed : TodoErrorContext()

    class ActionInsertionFailed : TodoErrorContext()

    class UndefinedAction : TodoErrorContext()
}