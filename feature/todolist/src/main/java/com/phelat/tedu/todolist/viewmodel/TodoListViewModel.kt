package com.phelat.tedu.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Deletable
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.designsystem.entity.BottomSheetItemEntity
import com.phelat.tedu.lifecycle.SingleLiveData
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todolist.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val dispatcher: Dispatcher,
    private val todoDataSourceUpdatable: Updatable.Suspendable<TodoEntity>,
    private val todoDataSourceDeletable: Deletable.Suspendable<TodoEntity>,
    private val todoDataSourceWritable: Writable.Suspendable<TodoEntity>,
    todoDataSourceReadable: Readable<Flow<List<TodoEntity>>>
) : ViewModel() {

    val todoObservable: LiveData<List<TodoEntity>> = todoDataSourceReadable.read()
        .debounce(UPDATE_DELAY_IN_MILLIS)
        .flowOn(dispatcher.iO)
        .asLiveData()

    private val _todoSheetObservable = SingleLiveData<List<BottomSheetItemEntity>>()
    val todoSheetObservable: LiveData<List<BottomSheetItemEntity>> = _todoSheetObservable

    private val _dismissTodoSheetObservable = SingleLiveData<Unit>()
    val dismissTodoSheetObservable: LiveData<Unit> = _dismissTodoSheetObservable

    private val _todoDeletionObservable = SingleLiveData<Unit>()
    val todoDeletionObservable: LiveData<Unit> = _todoDeletionObservable

    @Volatile
    private var deletedTodo: TodoEntity? = null

    fun onTodoClick(todoEntity: TodoEntity) {
        viewModelScope.launch(context = dispatcher.iO) {
            val updatedTodo = todoEntity.copy(isDone = todoEntity.isDone.not())
            delay(UPDATE_DELAY_IN_MILLIS)
            todoDataSourceUpdatable.update(updatedTodo)
        }
    }

    fun onTodoLongClick(todoEntity: TodoEntity) {
        _todoSheetObservable.value = listOf(
            BottomSheetItemEntity(
                itemIconResource = R.drawable.ic_delete_forever_icon_secondary_24dp,
                itemTitleResource = R.string.todolist_todo_sheet_delete,
                itemOnClickListener = { onDeleteTodoClick(todoEntity) }
            )
        )
    }

    private fun onDeleteTodoClick(todoEntity: TodoEntity) {
        _dismissTodoSheetObservable.call()
        viewModelScope.launch(context = dispatcher.iO) {
            delay(UPDATE_DELAY_IN_MILLIS)
            todoDataSourceDeletable.delete(todoEntity)
            deletedTodo = todoEntity
            _todoDeletionObservable.postCall()
        }
    }

    fun onTodoDeletionUndoClick() {
        viewModelScope.launch(context = dispatcher.iO) {
            delay(UPDATE_DELAY_IN_MILLIS)
            todoDataSourceWritable.write(requireNotNull(deletedTodo))
            deletedTodo = null
        }
    }

    companion object {
        private const val UPDATE_DELAY_IN_MILLIS = 100L
    }
}