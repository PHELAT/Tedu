package com.phelat.tedu.addtodo.di.module

import com.phelat.tedu.addtodo.viewmodel.AddTodoViewModel
import com.phelat.tedu.coroutines.di.module.ThreadModule
import com.phelat.tedu.todo.di.module.TodoModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object AddTodoModule {

    fun getModule() = module {
        loadKoinModules(listOf(ThreadModule.getModule(), TodoModule.getModule()))
        viewModel { AddTodoViewModel(get(), get(), get()) }
    }
}