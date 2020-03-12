package com.phelat.tedu.todolist.di.module

import com.phelat.tedu.coroutines.di.module.ThreadModule
import com.phelat.tedu.dependencyinjection.ModuleContainer
import com.phelat.tedu.todo.di.module.TodoModule
import com.phelat.tedu.todolist.viewmodel.TodoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object TodoListModule : ModuleContainer {

    override fun getModuleDependencies(): List<ModuleContainer> {
        return listOf(ThreadModule, TodoModule)
    }

    override fun getModule() = module {
        viewModel {
            TodoListViewModel(
                dispatcher = get(),
                todoDataSourceUpdatable = get(),
                todoDataSourceWritable = get(),
                todoDataSourceReadable = get(),
                todoDataSourceDeletable = get()
            )
        }
    }
}