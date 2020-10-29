package com.phelat.tedu.androiddagger

import dagger.android.DispatchingAndroidInjector

interface DispatcherComponent {

    fun dispatcher(): DispatchingAndroidInjector<Any>
}