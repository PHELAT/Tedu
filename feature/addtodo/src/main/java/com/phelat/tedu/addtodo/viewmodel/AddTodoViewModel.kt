package com.phelat.tedu.addtodo.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.addtodo.R
import com.phelat.tedu.addtodo.entity.SelectedDate
import com.phelat.tedu.addtodo.view.AddTodoViewState
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.functional.ifSuccessful
import com.phelat.tedu.functional.otherwise
import com.phelat.tedu.lifecycle.SingleLiveData
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.constant.TodoConstant
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import com.phelat.tedu.todo.repository.TodoRepository
import com.phelat.tedu.uiview.Navigate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import java.util.Date
import javax.inject.Inject

class AddTodoViewModel @Inject constructor(
    private val dispatcher: Dispatcher,
    private val todoRepository: TodoRepository,
    private val stringResourceProvider: ResourceProvider<StringId, StringResource>,
    private val dateToLocalDate: Mapper<Date, LocalDate>,
    private val selectedDateReadable: Readable<SelectedDate>
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

    fun onBundleReceive(bundle: Bundle?) {
        val todoForEdit = bundle?.getSerializable(TodoConstant.TODO_FOR_EDIT)
        if (todoForEdit is TodoEntity) {
            this.todoForEdit = todoForEdit
            if (_todoTextObservable.value == null) {
                _todoTextObservable.value = todoForEdit.todo
            }
        }
    }

    fun onSaveTodoClicked(typedTodo: String) {
        viewModelScope.launch(context = dispatcher.iO) {
            val selectedDate = selectedDateReadable.read().date
            val saveResponse = if (todoForEdit != null) {
                val editedTodo = requireNotNull(todoForEdit)
                    .copy(todo = typedTodo, date = dateToLocalDate.mapSecondToFirst(selectedDate))
                todoRepository.updateTodo(editedTodo)
            } else {
                val newTodo = TodoEntity(
                    todo = typedTodo,
                    date = dateToLocalDate.mapSecondToFirst(selectedDate),
                    todoId = System.currentTimeMillis()
                )
                todoRepository.addTodo(newTodo)
            }
            saveResponse.ifSuccessful {
                _navigationObservable.postValue(Navigate.Up)
            } otherwise { errorContext -> handleTodoErrorContext(errorContext) }
        }
    }

    private suspend fun handleTodoErrorContext(errorContext: TodoErrorContext) {
        withContext(dispatcher.main) {
            when (errorContext) {
                is TodoErrorContext.InsertionFailed, is TodoErrorContext.UpdateFailed -> {
                    val message = stringResourceProvider.getResource(
                        StringId(R.string.general_failure_message)
                    )
                    _snackBarObservable.value = message.resource
                }
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