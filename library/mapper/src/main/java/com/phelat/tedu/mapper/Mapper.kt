package com.phelat.tedu.mapper

interface Mapper<First, Second> {

    fun mapFirstToSecond(first: First): Second

    fun mapSecondToFirst(second: Second): First
}
