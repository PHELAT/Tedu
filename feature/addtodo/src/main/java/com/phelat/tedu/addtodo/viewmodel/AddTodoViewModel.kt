package com.phelat.tedu.addtodo.viewmodel

import androidx.lifecycle.ViewModel
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.entity.TodoEntity

class AddTodoViewModel(private val todoDataSourceWritable: Writable<TodoEntity>) : ViewModel() {

    fun onSaveTodoClicked(todo: String) {
        todoDataSourceWritable.write(TodoEntity(todo))
    }
}