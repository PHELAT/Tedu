package com.phelat.tedu.todo.error

sealed class TodoArchivableErrorContext : Throwable() {

    class DeletionFailed : TodoArchivableErrorContext()
}