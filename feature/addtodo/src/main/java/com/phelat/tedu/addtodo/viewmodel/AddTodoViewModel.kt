package com.phelat.tedu.addtodo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.coroutines.launch

class AddTodoViewModel(
    private val dispatcher: Dispatcher,
    private val todoDataSourceWritable: Writable.Suspendable<TodoEntity>
) : ViewModel() {

    fun onSaveTodoClicked(todo: String) {
        viewModelScope.launch(context = dispatcher.iO) {
            todoDataSourceWritable.write(TodoEntity(todo = todo))
        }
    }
}