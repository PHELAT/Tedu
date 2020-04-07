package com.phelat.tedu.androiddagger

import com.phelat.tedu.dependencyinjection.feature.FeatureStartUpModule
import dagger.Module
import dagger.android.AndroidInjectionModule

@Module(includes = [AndroidInjectionModule::class, FeatureStartUpModule::class])
interface FeatureModule