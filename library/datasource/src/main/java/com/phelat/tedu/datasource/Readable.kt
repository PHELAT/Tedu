package com.phelat.tedu.datasource

interface Readable<O> {

    fun read(): O

    interface IO<I, O> {

        fun read(input: I): O

    }

}