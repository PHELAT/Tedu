package com.phelat.tedu.todolist.di.component

import com.phelat.tedu.todo.di.component.TodoComponent
import dagger.Component

@Component(dependencies = [TodoComponent::class])
interface TodoListComponent