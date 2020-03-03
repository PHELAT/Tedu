package com.phelat.tedu.todolist.di.component

import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.daggerandroid.DispatcherComponent
import com.phelat.tedu.todo.di.component.TodoComponent
import com.phelat.tedu.todolist.di.module.TodoListBindingModule
import com.phelat.tedu.todolist.di.scope.TodoListScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@TodoListScope
@Component(
    modules = [AndroidInjectionModule::class, TodoListBindingModule::class],
    dependencies = [TodoComponent::class]
)
interface TodoListComponent : DispatcherComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindDispatcher(dispatcher: Dispatcher): Builder
        fun bindTodoComponent(todoComponent: TodoComponent): Builder
        fun build(): TodoListComponent
    }
}