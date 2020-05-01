package com.phelat.tedu.addtodo.di.module

import androidx.lifecycle.ViewModel
import com.phelat.tedu.addtodo.di.scope.AddTodoScope
import com.phelat.tedu.addtodo.viewmodel.AddTodoViewModel
import com.phelat.tedu.addtodo.viewmodel.DateViewModel
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.phelat.tedu.lifecycle.ViewModelKey
import com.phelat.tedu.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface AddTodoViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddTodoViewModel::class)
    fun bindAddTodoViewModel(input: AddTodoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DateViewModel::class)
    fun bindDateViewModel(input: DateViewModel): ViewModel

    companion object {
        @Provides
        @AddTodoScope
        fun provideViewModelFactory(viewModelProviders: ViewModelProviders): ViewModelFactory {
            return ViewModelFactory(viewModelProviders)
        }
    }
}