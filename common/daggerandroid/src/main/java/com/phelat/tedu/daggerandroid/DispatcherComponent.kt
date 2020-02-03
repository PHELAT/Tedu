package com.phelat.tedu.daggerandroid

import dagger.android.DispatchingAndroidInjector

interface DispatcherComponent {

    fun dispatcher(): DispatchingAndroidInjector<Any>
}