package com.phelat.tedu.addtodo.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.addtodo.R
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.functional.Response
import com.phelat.tedu.functional.ifSuccessful
import com.phelat.tedu.functional.otherwise
import com.phelat.tedu.lifecycle.SingleLiveData
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.constant.TodoConstant
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todo.error.TodoErrorContext
import com.phelat.tedu.uiview.Navigate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import java.util.Date

class AddTodoViewModel(
    private val dispatcher: Dispatcher,
    private val todoDataSourceWritable: Writable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>,
    private val todoDataSourceUpdatable: Updatable.Suspendable.IO<TodoEntity, Response<Unit, TodoErrorContext>>,
    private val stringResourceProvider: ResourceProvider<StringId, StringResource>,
    private val dateToLocalDate: Mapper<Date, LocalDate>
) : ViewModel() {

    private val _todoTextObservable = SingleLiveData<String>()
    val todoTextObservable: LiveData<String> = _todoTextObservable

    private val _todoDateObservable = MutableLiveData<String>()
    val todoDateObservable: LiveData<String> = _todoDateObservable

    private val _todoDateSheetObservable = SingleLiveData<Unit>()
    val todoDateSheetObservable: LiveData<Unit> = _todoDateSheetObservable

    private val _navigationObservable = SingleLiveData<Navigate>()
    val navigationObservable: LiveData<Navigate> = _navigationObservable

    private val _snackBarObservable = SingleLiveData<String>()
    val snackBarObservable: LiveData<String> = _snackBarObservable

    private var todoForEdit: TodoEntity? = null

    private var selectedDate: LocalDate = LocalDate.now()

    fun onBundleReceive(bundle: Bundle?) {
        val todoForEdit = bundle?.getSerializable(TodoConstant.TODO_FOR_EDIT)
        if (todoForEdit is TodoEntity) {
            this.todoForEdit = todoForEdit
            if (_todoTextObservable.value == null) {
                _todoTextObservable.value = todoForEdit.todo
            }
            if (_todoDateObservable.value == null) {
                val date = dateToLocalDate.mapFirstToSecond(todoForEdit.date)
                onDateSelect(date)
            }
        }
    }

    fun onSaveTodoClicked(typedTodo: String) {
        viewModelScope.launch(context = dispatcher.iO) {
            val saveResponse = if (todoForEdit != null) {
                val editedTodo = requireNotNull(todoForEdit)
                    .copy(todo = typedTodo, date = dateToLocalDate.mapSecondToFirst(selectedDate))
                todoDataSourceUpdatable.update(editedTodo)
            } else {
                val newTodo = TodoEntity(
                    todo = typedTodo,
                    date = dateToLocalDate.mapSecondToFirst(selectedDate)
                )
                todoDataSourceWritable.write(newTodo)
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
                        StringId(R.string.todo_insertion_failed_message)
                    )
                    _snackBarObservable.value = message.resource
                }
            }
        }
    }

    fun onDateSelect(selectedDate: LocalDate) {
        this.selectedDate = selectedDate
        val today = LocalDate.now()
        _todoDateObservable.value = when {
            selectedDate == today -> {
                stringResourceProvider.getResource(StringId(R.string.addtodo_date_today_text)).resource
            }
            isSelectedDateTomorrow(today, selectedDate) -> {
                stringResourceProvider.getResource(StringId(R.string.addtodo_date_tomorrow_text)).resource
            }
            else -> {
                "${selectedDate.year}/${selectedDate.monthValue}/${selectedDate.dayOfMonth}"
            }
        }
    }

    private fun isSelectedDateTomorrow(today: LocalDate, selectedDate: LocalDate): Boolean {
        return (today.year == selectedDate.year)
            .and(today.monthValue == selectedDate.monthValue)
            .and(selectedDate.dayOfMonth - today.dayOfMonth == 1)
    }

    fun onSelectDateClick() {
        _todoDateSheetObservable.call()
    }

    fun getSelectedDate(): LocalDate {
        // TODO: refactor this
        return selectedDate
    }
}