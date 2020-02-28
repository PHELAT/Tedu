package com.phelat.tedu.datasource

interface Updatable<I> {

    fun update(input: I)

    interface IO<I, O> {

        fun update(input: I): O

    }

}