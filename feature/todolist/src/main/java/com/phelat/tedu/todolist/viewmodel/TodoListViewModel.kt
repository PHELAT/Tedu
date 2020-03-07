package com.phelat.tedu.todolist.viewmodel

import android.os.Bundle
import androidx.annotation.IdRes
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
import com.phelat.tedu.todo.constant.TodoConstant
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todolist.R
import com.phelat.tedu.todolist.view.AddTodoItem
import com.phelat.tedu.todolist.view.TodoListItem
import com.xwray.groupie.Section
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val dispatcher: Dispatcher,
    private val todoDataSourceUpdatable: Updatable.Suspendable<TodoEntity>,
    private val todoDataSourceDeletable: Deletable.Suspendable<TodoEntity>,
    private val todoDataSourceWritable: Writable.Suspendable<TodoEntity>,
    todoDataSourceReadable: Readable<Flow<List<TodoEntity>>>
) : ViewModel() {

    private val headerSection = Section().apply {
        add(AddTodoItem(onAddTodoClickListener = ::onAddTodoClick))
    }
    private val todoSection = Section()

    val todoObservable: LiveData<List<Section>> = todoDataSourceReadable.read()
        .debounce(UPDATE_DELAY_IN_MILLIS)
        .map { todoEntities ->
            todoEntities.map { todoEntity ->
                TodoListItem(
                    todoEntity,
                    onClickListener = ::onTodoClick,
                    onLongClickListener = ::onTodoLongClick
                )
            }
        }
        .flowOn(dispatcher.iO)
        .onEach {
            todoSection.update(it)
        }
        .map {
            listOf(headerSection, todoSection)
        }
        .flowOn(dispatcher.main)
        .asLiveData()

    private val _todoSheetObservable = SingleLiveData<List<BottomSheetItemEntity>>()
    val todoSheetObservable: LiveData<List<BottomSheetItemEntity>> = _todoSheetObservable

    private val _dismissTodoSheetObservable = SingleLiveData<Unit>()
    val dismissTodoSheetObservable: LiveData<Unit> = _dismissTodoSheetObservable

    private val _todoDeletionObservable = SingleLiveData<Unit>()
    val todoDeletionObservable: LiveData<Unit> = _todoDeletionObservable

    private val _navigationObservable = SingleLiveData<Pair<@IdRes Int, Bundle?>>()
    val navigationObservable: LiveData<Pair<Int, Bundle?>> = _navigationObservable

    @Volatile
    private var deletedTodo: TodoEntity? = null

    private fun onTodoClick(todoEntity: TodoEntity) {
        viewModelScope.launch(context = dispatcher.iO) {
            val updatedTodo = todoEntity.copy(isDone = todoEntity.isDone.not())
            delay(UPDATE_DELAY_IN_MILLIS)
            todoDataSourceUpdatable.update(updatedTodo)
        }
    }

    private fun onTodoLongClick(todoEntity: TodoEntity) {
        _todoSheetObservable.value = listOf(
            BottomSheetItemEntity(
                itemIconResource = R.drawable.ic_edit_icon_secondary_24dp,
                itemTitleResource = R.string.todolist_todo_sheet_edit,
                itemOnClickListener = { onEditTodoClick(todoEntity) }
            ),
            BottomSheetItemEntity(
                itemIconResource = R.drawable.ic_delete_forever_icon_secondary_24dp,
                itemTitleResource = R.string.todolist_todo_sheet_delete,
                itemOnClickListener = { onDeleteTodoClick(todoEntity) }
            )
        )
    }

    private fun onEditTodoClick(todoEntity: TodoEntity) {
        val editBundle = Bundle()
        editBundle.putSerializable(TodoConstant.TODO_FOR_EDIT, todoEntity)
        _navigationObservable.value = R.id.navigation_addtodo to editBundle
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

    private fun onAddTodoClick() {
        _navigationObservable.value = R.id.navigation_addtodo to null
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