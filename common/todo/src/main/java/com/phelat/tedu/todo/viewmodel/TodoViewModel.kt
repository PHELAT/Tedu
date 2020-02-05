package com.phelat.tedu.todo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class TodoViewModel(
    private val todoDataSourceWritable: Writable<TodoEntity>,
    todoDataSourceReadable: Readable<Flow<List<TodoEntity>>>
) : ViewModel() {

    val todoObservable: LiveData<List<TodoEntity>> = todoDataSourceReadable.read()
        .flowOn(Dispatchers.IO)
        .asLiveData()

    fun onSaveTodoClicked(todo: String) {
        todoDataSourceWritable.write(TodoEntity(todo))
    }
}