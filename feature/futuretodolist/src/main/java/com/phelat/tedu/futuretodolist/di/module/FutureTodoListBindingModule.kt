package com.phelat.tedu.futuretodolist.di.module

import com.phelat.tedu.futuretodolist.di.scope.FutureTodoListScope
import com.phelat.tedu.futuretodolist.view.FutureTodoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FutureTodoListBindingModule {

    @FutureTodoListScope
    @ContributesAndroidInjector(modules = [FutureTodoListViewModelModule::class])
    fun bindFutureTodoListFragment(): FutureTodoListFragment
}