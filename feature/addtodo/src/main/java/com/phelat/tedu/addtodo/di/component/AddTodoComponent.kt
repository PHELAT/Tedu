package com.phelat.tedu.addtodo.di.component

import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.di.component.TodoComponent
import com.phelat.tedu.todo.entity.TodoEntity
import dagger.Component

@Component(dependencies = [TodoComponent::class])
interface AddTodoComponent {

    fun writable(): Writable<TodoEntity>
}