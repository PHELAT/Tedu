package com.phelat.tedu.addtodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.addtodo.R
import com.phelat.tedu.addtodo.di.scope.AddTodoScope
import com.phelat.tedu.addtodo.entity.SelectedDate
import com.phelat.tedu.addtodo.view.AddTodoViewState
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringArg
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.functional.ifSuccessful
import com.phelat.tedu.functional.otherwise
import com.phelat.tedu.lifecycle.SingleLiveData
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.navigation.Navigate
import com.phelat.tedu.todo.entity.Action
import com.phelat.tedu.todo.entity.ActionEntity
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import com.phelat.tedu.todo.repository.TodoRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import java.util.Date
import javax.inject.Inject

@AddTodoScope
class AddTodoViewModel @Inject constructor(
    private val dispatcher: Dispatcher,
    private val todoRepository: TodoRepository,
    private val stringResourceProvider: ResourceProvider<StringId, StringResource>,
    private val stringArgResourceProvider: ResourceProvider<StringArg, StringResource>,
    private val dateToLocalDate: Mapper<Date, LocalDate>,
    private val selectedDateReadable: Readable<SelectedDate>,
    private val localDateToTeduDate: Mapper<LocalDate, TeduDate>
) : ViewModel() {

    private val _todoTextObservable = SingleLiveData<String>()
    val todoTextObservable: LiveData<String> = _todoTextObservable

    private val _navigationObservable = SingleLiveData<Navigate>()
    val navigationObservable: LiveData<Navigate> = _navigationObservable

    private val _snackBarObservable = SingleLiveData<String>()
    val snackBarObservable: LiveData<String> = _snackBarObservable

    private val _viewStateObservable = MutableLiveData(AddTodoViewState())
    val viewStateObservable: LiveData<AddTodoViewState> = _viewStateObservable

    private var todoForEdit: TodoEntity? = null

    fun onFragmentArgumentReceived(fragmentArgument: TodoEntity) {
        this.todoForEdit = fragmentArgument
        if (_todoTextObservable.value == null) {
            _todoTextObservable.value = fragmentArgument.todo
        }
    }

    fun onSaveTodoClicked(typedTodo: String) {
        viewModelScope.launch {
            val selectedDate = selectedDateReadable.read().date
            val action = getSaveAction(typedTodo, selectedDate)
            withContext(context = dispatcher.iO) { todoRepository.processAction(action) }
                .ifSuccessful {
                    val teduDate = localDateToTeduDate.mapFirstToSecond(selectedDate)
                    if (teduDate != TeduDate.Today) {
                        showScheduledTodoSnackBar(teduDate)
                    }
                    _navigationObservable.value = Navigate.Up
                }
                .otherwise { errorContext ->
                    handleTodoErrorContext(errorContext)
                }
        }
    }

    private fun showScheduledTodoSnackBar(teduDate: TeduDate) {
        val humanReadableDate = when (teduDate) {
            is TeduDate.Today -> {
                stringResourceProvider.getResource(StringId(R.string.addtodo_date_today_text))
                    .resource
            }
            is TeduDate.Tomorrow -> {
                stringResourceProvider.getResource(StringId(R.string.addtodo_date_tomorrow_text))
                    .resource
            }
            is TeduDate.HumanReadableDate -> {
                teduDate.date
            }
        }
        val stringId = StringArg(R.string.addtodo_scheduled_todo, humanReadableDate)
        stringArgResourceProvider.getResource(stringId)
            .resource
            .also(_snackBarObservable::setValue)
    }

    private fun getSaveAction(typedTodo: String, selectedDate: LocalDate): ActionEntity {
        return if (todoForEdit != null) {
            val editedTodo = requireNotNull(todoForEdit)
                .copy(todo = typedTodo, date = dateToLocalDate.mapSecondToFirst(selectedDate))
            ActionEntity(
                action = Action.Update,
                timestamp = System.currentTimeMillis(),
                data = editedTodo
            )
        } else {
            val newTodo = TodoEntity(
                todo = typedTodo,
                date = dateToLocalDate.mapSecondToFirst(selectedDate),
                todoId = System.currentTimeMillis()
            )
            ActionEntity(
                action = Action.Add,
                timestamp = System.currentTimeMillis(),
                data = newTodo
            )
        }
    }

    private fun handleTodoErrorContext(errorContext: TodoErrorContext) {
        when (errorContext) {
            is TodoErrorContext.InsertionFailed, is TodoErrorContext.UpdateFailed -> {
                val message = stringResourceProvider.getResource(
                    StringId(R.string.general_failure_message)
                )
                _snackBarObservable.value = message.resource
            }
        }
    }

    fun onTodoTextChange(text: CharSequence?) {
        _viewStateObservable.value = if (text?.isNotEmpty() == true) {
            requireNotNull(_viewStateObservable.value)
                .copy(isSaveTodoButtonEnabled = true)
        } else {
            requireNotNull(_viewStateObservable.value)
                .copy(isSaveTodoButtonEnabled = false)
        }
    }
}