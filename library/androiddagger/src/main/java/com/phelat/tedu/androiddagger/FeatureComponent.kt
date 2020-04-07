package com.phelat.tedu.androiddagger

import dagger.android.DispatchingAndroidInjector

interface FeatureComponent {

    fun dispatcher(): DispatchingAndroidInjector<Any>
}