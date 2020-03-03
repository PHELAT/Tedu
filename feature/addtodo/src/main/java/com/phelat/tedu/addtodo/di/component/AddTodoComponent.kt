package com.phelat.tedu.addtodo.di.component

import com.phelat.tedu.addtodo.di.module.AddTodoBindingModule
import com.phelat.tedu.addtodo.di.scope.AddTodoScope
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.daggerandroid.DispatcherComponent
import com.phelat.tedu.todo.di.component.TodoComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@AddTodoScope
@Component(
    modules = [AndroidInjectionModule::class, AddTodoBindingModule::class],
    dependencies = [TodoComponent::class]
)
interface AddTodoComponent : DispatcherComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindDispatcher(dispatcher: Dispatcher): Builder
        fun bindTodoComponent(todoComponent: TodoComponent): Builder
        fun build(): AddTodoComponent
    }
}