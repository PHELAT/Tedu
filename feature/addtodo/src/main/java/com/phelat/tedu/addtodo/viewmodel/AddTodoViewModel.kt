package com.phelat.tedu.addtodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTodoViewModel(
    private val todoDataSourceWritable: Writable.Suspendable<TodoEntity>
) : ViewModel() {

    fun onSaveTodoClicked(todo: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            todoDataSourceWritable.write(TodoEntity(todo = todo))
        }
    }
}