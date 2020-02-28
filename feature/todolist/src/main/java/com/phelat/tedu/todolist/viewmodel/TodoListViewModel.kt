package com.phelat.tedu.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class TodoListViewModel(todoDataSourceReadable: Readable<Flow<List<TodoEntity>>>) : ViewModel() {

    val todoObservable: LiveData<List<TodoEntity>> = todoDataSourceReadable.read()
        .flowOn(Dispatchers.IO)
        .asLiveData()
}