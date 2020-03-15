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
import com.phelat.tedu.lifecycle.SingleLiveData
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.constant.TodoConstant
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.uiview.Navigate
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import java.util.Date

class AddTodoViewModel(
    private val dispatcher: Dispatcher,
    private val todoDataSourceWritable: Writable.Suspendable<TodoEntity>,
    private val todoDataSourceUpdatable: Updatable.Suspendable<TodoEntity>,
    private val stringResourceProvider: ResourceProvider<StringId, StringResource>,
    private val dateToLocalDate: Mapper<Date, LocalDate>
) : ViewModel() {

    // FIXME: resets edited text on config change
    private val _todoTextObservable = MutableLiveData<String>()
    val todoTextObservable: LiveData<String> = _todoTextObservable

    private val _todoDateObservable = MutableLiveData<String>()
    val todoDateObservable: LiveData<String> = _todoDateObservable

    private val _todoDateSheetObservable = SingleLiveData<Unit>()
    val todoDateSheetObservable: LiveData<Unit> = _todoDateSheetObservable

    private val _navigationObservable = SingleLiveData<Navigate>()
    val navigationObservable: LiveData<Navigate> = _navigationObservable

    private var todoForEdit: TodoEntity? = null

    private var selectedDate: LocalDate = LocalDate.now()

    fun onBundleReceive(bundle: Bundle?) {
        val todoForEdit = bundle?.getSerializable(TodoConstant.TODO_FOR_EDIT)
        if (todoForEdit is TodoEntity) {
            this.todoForEdit = todoForEdit
            _todoTextObservable.value = todoForEdit.todo
            val date = dateToLocalDate.mapFirstToSecond(todoForEdit.date)
            onDateSelect(date)
        }
    }

    fun onSaveTodoClicked(todo: String) {
        viewModelScope.launch(context = dispatcher.iO) {
            if (todoForEdit != null) {
                val updatedTodo = requireNotNull(todoForEdit)
                    .copy(todo, dateToLocalDate.mapSecondToFirst(selectedDate))
                todoDataSourceUpdatable.update(updatedTodo)
            } else {
                todoDataSourceWritable.write(
                    TodoEntity(
                        todo = todo,
                        date = dateToLocalDate.mapSecondToFirst(selectedDate)
                    )
                )
            }
            _navigationObservable.postValue(Navigate.Up)
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