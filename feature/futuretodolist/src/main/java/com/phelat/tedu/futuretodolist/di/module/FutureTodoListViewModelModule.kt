package com.phelat.tedu.futuretodolist.di.module

import androidx.lifecycle.ViewModel
import com.phelat.tedu.futuretodolist.di.scope.FutureTodoListScope
import com.phelat.tedu.futuretodolist.viewmodel.FutureTodoListViewModel
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.phelat.tedu.lifecycle.ViewModelKey
import com.phelat.tedu.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface FutureTodoListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FutureTodoListViewModel::class)
    @FutureTodoListScope
    fun bindFutureTodoListViewModel(input: FutureTodoListViewModel): ViewModel

    companion object {
        @Provides
        @FutureTodoListScope
        fun provideViewModelFactory(viewModelProviders: ViewModelProviders): ViewModelFactory {
            return ViewModelFactory(viewModelProviders)
        }
    }
}