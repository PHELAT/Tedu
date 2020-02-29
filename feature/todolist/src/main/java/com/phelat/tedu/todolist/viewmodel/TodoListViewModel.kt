package com.phelat.tedu.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val todoDataSourceUpdatable: Updatable.Suspendable<TodoEntity>,
    todoDataSourceReadable: Readable<Flow<List<TodoEntity>>>
) : ViewModel() {

    val todoObservable: LiveData<List<TodoEntity>> = todoDataSourceReadable.read()
        .debounce(UPDATE_DELAY_IN_MILLIS)
        .flowOn(Dispatchers.IO)
        .asLiveData()

    fun onTodoClick(todoEntity: TodoEntity) {
        viewModelScope.launch(context = Dispatchers.IO) {
            val updatedTodo = todoEntity.copy(isDone = todoEntity.isDone.not())
            delay(UPDATE_DELAY_IN_MILLIS)
            todoDataSourceUpdatable.update(updatedTodo)
        }
    }

    companion object {
        private const val UPDATE_DELAY_IN_MILLIS = 100L
    }
}