package com.phelat.tedu.todolist.di.module

import com.phelat.tedu.coroutines.di.module.ThreadModule
import com.phelat.tedu.todo.di.module.TodoModule
import com.phelat.tedu.todolist.viewmodel.TodoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object TodoListModule {

    fun getModule() = module {
        loadKoinModules(listOf(ThreadModule.getModule(), TodoModule.getModule()))
        viewModel { TodoListViewModel(get(), get(), get(), get(), get()) }
    }
}