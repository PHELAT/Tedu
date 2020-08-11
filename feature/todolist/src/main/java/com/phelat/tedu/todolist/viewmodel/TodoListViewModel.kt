package com.phelat.tedu.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.analytics.ExceptionLogger
import com.phelat.tedu.analytics.di.qualifier.NonFatal
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.date.datasource.DateDataSourceReadable
import com.phelat.tedu.designsystem.entity.BottomSheetEntity
import com.phelat.tedu.designsystem.entity.BottomSheetItemEntity
import com.phelat.tedu.functional.ifNotSuccessful
import com.phelat.tedu.functional.ifSuccessful
import com.phelat.tedu.functional.mapForEach
import com.phelat.tedu.functional.otherwise
import com.phelat.tedu.lifecycle.SingleLiveData
import com.phelat.tedu.navigation.Navigate
import com.phelat.tedu.todo.entity.Action
import com.phelat.tedu.todo.entity.ActionEntity
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.repository.TodoRepository
import com.phelat.tedu.todolist.R
import com.phelat.tedu.todolist.di.scope.TodoListScope
import com.phelat.tedu.todolist.view.AddTodoItem
import com.phelat.tedu.todolist.view.TodoListItem
import com.xwray.groupie.Section
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@TodoListScope
class TodoListViewModel @Inject constructor(
    private val dispatcher: Dispatcher,
    private val todoRepository: TodoRepository,
    private val dateDataSourceReadable: DateDataSourceReadable,
    private val stringResourceProvider: ResourceProvider<StringId, StringResource>,
    @NonFatal private val nonFatalLogger: ExceptionLogger
) : ViewModel() {

    private val headerSection = Section().apply {
        add(AddTodoItem(onAddTodoClickListener = ::onAddTodoClick))
    }
    private val todoSection = Section()

    private val _todoObservable = MutableLiveData<List<Section>>()
    val todoObservable: LiveData<List<Section>> = _todoObservable

    private val _todoSheetObservable = SingleLiveData<BottomSheetEntity>()
    val todoSheetObservable: LiveData<BottomSheetEntity> = _todoSheetObservable

    private val _dismissTodoSheetObservable = SingleLiveData<Unit>()
    val dismissTodoSheetObservable: LiveData<Unit> = _dismissTodoSheetObservable

    private val _todoDeletionObservable = SingleLiveData<Unit>()
    val todoDeletionObservable: LiveData<Unit> = _todoDeletionObservable

    private val _navigationObservable = SingleLiveData<Navigate>()
    val navigationObservable: LiveData<Navigate> = _navigationObservable

    private val _snackBarObservable = SingleLiveData<String>()
    val snackBarObservable: LiveData<String> = _snackBarObservable

    @Volatile
    private var deletedTodo: TodoEntity? = null

    init {
        viewModelScope.launch {
            fetchTodos()
        }
    }

    private suspend fun fetchTodos() {
        val tomorrow = dateDataSourceReadable.read(TeduDate.Tomorrow)
        todoRepository.getTodos(tomorrow)
            .onEach { delay(UPDATE_DELAY_IN_MILLIS) }
            .mapForEach { todoEntity ->
                TodoListItem(
                    todoEntity,
                    onClickListener = ::onTodoClick,
                    onLongClickListener = ::onTodoLongClick
                )
            }
            .flowOn(dispatcher.iO)
            .onEach { todoSection.update(it) }
            .map { listOf(headerSection, todoSection) }
            .flowOn(dispatcher.main)
            .collect { todos -> _todoObservable.postValue(todos) }
    }

    private fun onTodoClick(todoEntity: TodoEntity) {
        viewModelScope.launch(context = dispatcher.iO) {
            val updatedTodo = todoEntity.copy(isDone = todoEntity.isDone.not())
            delay(UPDATE_DELAY_IN_MILLIS)
            val updateAction = ActionEntity(
                action = Action.Update,
                timestamp = System.currentTimeMillis(),
                data = updatedTodo
            )
            todoRepository.processAction(updateAction).ifNotSuccessful { error ->
                nonFatalLogger.log(error)
                showGeneralFailureMessage()
            }
        }
    }

    private suspend fun showGeneralFailureMessage() {
        withContext(dispatcher.main) {
            val message = stringResourceProvider.getResource(
                StringId(R.string.general_failure_message)
            )
            _snackBarObservable.value = message.resource
        }
    }

    private fun onTodoLongClick(todoEntity: TodoEntity) {
        _todoSheetObservable.value = BottomSheetEntity(
            items = listOf(
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
            ),
            sheetTitle = todoEntity.todo
        )
    }

    private fun onEditTodoClick(todoEntity: TodoEntity) {
        val deepLink = stringResourceProvider.getResource(StringId(R.string.deeplink_edittodo))
            .resource
        _navigationObservable.value = Navigate.ToSerializableDeepLink(
            deepLink,
            todoEntity
        )
    }

    private fun onDeleteTodoClick(todoEntity: TodoEntity) {
        _dismissTodoSheetObservable.call()
        viewModelScope.launch(context = dispatcher.iO) {
            delay(UPDATE_DELAY_IN_MILLIS)
            val deleteAction = ActionEntity(
                action = Action.Delete,
                timestamp = System.currentTimeMillis(),
                data = todoEntity
            )
            todoRepository.processAction(deleteAction).ifSuccessful {
                deletedTodo = todoEntity
                _todoDeletionObservable.postCall()
            } otherwise { error ->
                nonFatalLogger.log(error)
                showGeneralFailureMessage()
            }
        }
    }

    private fun onAddTodoClick() {
        val deepLinkId = StringId(R.string.deeplink_addtodo)
        stringResourceProvider.getResource(deepLinkId)
            .resource
            .run(Navigate::ToDeepLink)
            .also(_navigationObservable::setValue)
    }

    fun onTodoDeletionUndoClick() {
        viewModelScope.launch(context = dispatcher.iO) {
            delay(UPDATE_DELAY_IN_MILLIS)
            val addAction = ActionEntity(
                action = Action.Add,
                timestamp = System.currentTimeMillis(),
                data = requireNotNull(deletedTodo)
            )
            todoRepository.processAction(addAction)
            deletedTodo = null
        }
    }

    companion object {
        private const val UPDATE_DELAY_IN_MILLIS = 100L
    }
}