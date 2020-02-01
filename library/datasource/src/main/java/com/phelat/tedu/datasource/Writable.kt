package com.phelat.tedu.datasource

interface Writable<I> {

    fun write(input: I)

    interface IO<I, O> {

        fun write(input: I): O

    }

}