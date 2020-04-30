package com.phelat.tedu.addtodo.di.module

import androidx.lifecycle.ViewModel
import com.phelat.tedu.addtodo.viewmodel.AddTodoViewModel
import com.phelat.tedu.addtodo.viewmodel.DateViewModel
import com.phelat.tedu.lifecycle.ViewModelKey
import dagger.Binds
import dagger.Module
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
}