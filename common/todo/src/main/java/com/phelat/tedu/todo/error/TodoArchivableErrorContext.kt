package com.phelat.tedu.todo.error

sealed class TodoArchivableErrorContext {

    object DeletionFailed : TodoArchivableErrorContext()
}