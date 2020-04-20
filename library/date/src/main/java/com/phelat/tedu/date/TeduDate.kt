package com.phelat.tedu.date

sealed class TeduDate {

    object Today : TeduDate()

    object Tomorrow : TeduDate()
}