package com.phelat.tedu.addtodo.di.module

import com.phelat.tedu.addtodo.viewmodel.AddTodoViewModel
import com.phelat.tedu.androidresource.di.AndroidResourceModule
import com.phelat.tedu.coroutines.di.module.ThreadModule
import com.phelat.tedu.dependencyinjection.ModuleContainer
import com.phelat.tedu.todo.di.module.TodoModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AddTodoModule : ModuleContainer {

    override fun getModuleDependencies(): List<ModuleContainer> {
        return listOf(ThreadModule, TodoModule, AndroidResourceModule)
    }

    override fun getModule() = module {
        viewModel {
            AddTodoViewModel(
                dispatcher = get(),
                todoDataSourceWritable = get(),
                todoDataSourceUpdatable = get(),
                stringResourceProvider = get()
            )
        }
    }
}