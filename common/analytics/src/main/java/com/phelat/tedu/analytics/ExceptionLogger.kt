package com.phelat.tedu.analytics

interface ExceptionLogger {

    fun log(throwable: Throwable)
}