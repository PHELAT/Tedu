package com.phelat.tedu.todolist.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import com.phelat.tedu.todo.type.ArchivableTodos
import com.phelat.tedu.todolist.R
import com.phelat.tedu.todolist.view.AddTodoItem
import com.phelat.tedu.todolist.view.TodoListItem
import com.phelat.tedu.uiview.DirectionId
import com.phelat.tedu.uiview.Navigate
import com.xwray.groupie.Section
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import java.util.Date

class TodoListViewModel(
    private val dispatcher: Dispatcher,
    private val todoDataSourceUpdatable: Updatable.Suspendable<TodoEntity>,
    private val todoDataSourceDeletable: Deletable.Suspendable<TodoEntity>,
    private val todoDataSourceWritable: Writable.Suspendable<TodoEntity>,
    private val todoDataSourceReadable: Readable.IO<Date, Flow<List<TodoEntity>>>,
    private val archivableTodoDataSourceReadable: Readable.Suspendable.IO<Date, ArchivableTodos>,
    private val archivableTodoDataSourceDeletable: Deletable.Suspendable.IO<ArchivableTodos, Boolean>
) : ViewModel() {

    private val headerSection = Section().apply {
        add(AddTodoItem(onAddTodoClickListener = ::onAddTodoClick))
    }
    private val todoSection = Section()

    private val _todoObservable = MutableLiveData<List<Section>>()
    val todoObservable: LiveData<List<Section>> = _todoObservable

    private val _todoSheetObservable = SingleLiveData<List<BottomSheetItemEntity>>()
    val todoSheetObservable: LiveData<List<BottomSheetItemEntity>> = _todoSheetObservable

    private val _dismissTodoSheetObservable = SingleLiveData<Unit>()
    val dismissTodoSheetObservable: LiveData<Unit> = _dismissTodoSheetObservable

    private val _todoDeletionObservable = SingleLiveData<Unit>()
    val todoDeletionObservable: LiveData<Unit> = _todoDeletionObservable

    private val _navigationObservable = SingleLiveData<Navigate>()
    val navigationObservable: LiveData<Navigate> = _navigationObservable

    @Volatile
    private var deletedTodo: TodoEntity? = null

    init {
        viewModelScope.launch(context = dispatcher.iO) {
            deleteArchivableTodos()
            fetchTodos()
        }
    }

    private suspend fun deleteArchivableTodos() {
        val today = getTodayDate()
        val todosToDelete = archivableTodoDataSourceReadable.read(today)
        if (todosToDelete.isNotEmpty()) {
            archivableTodoDataSourceDeletable.delete(todosToDelete)
        }
    }

    private fun getTodayDate(): Date {
        // TODO: move date logic to a data source class
        val today = LocalDate.now()
            .atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        return Date(today)
    }

    private suspend fun fetchTodos() {
        val tomorrow = getTomorrowDate()
        todoDataSourceReadable.read(tomorrow)
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
            .onEach { todoSection.update(it) }
            .map { listOf(headerSection, todoSection) }
            .flowOn(dispatcher.main)
            .collect { todos -> _todoObservable.postValue(todos) }
    }

    private fun getTomorrowDate(): Date {
        // TODO: move date logic to a data source class
        val today = LocalDate.now(ZoneId.systemDefault())
            .atStartOfDay()
        val tomorrow = today.plusDays(1)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
        return Date(tomorrow)
    }

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
        _navigationObservable.value = Navigate.ToDirection(
            DirectionId(R.id.navigation_addtodo),
            editBundle
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

    private fun onAddTodoClick() {
        _navigationObservable.value = Navigate.ToDirection(DirectionId(R.id.navigation_addtodo))
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