package com.phelat.tedu.todo.error

sealed class TodoArchivableErrorContext : Throwable() {

    object DeletionFailed : TodoArchivableErrorContext()
}