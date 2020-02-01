package com.phelat.tedu.di.component

import com.phelat.tedu.Tedu
import com.phelat.tedu.di.scope.ApplicationScope
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@ApplicationScope
@Component(modules = [AndroidInjectionModule::class])
interface TeduComponent : AndroidInjector<Tedu>