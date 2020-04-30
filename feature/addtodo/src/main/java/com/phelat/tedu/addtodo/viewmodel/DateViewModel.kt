package com.phelat.tedu.addtodo.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phelat.tedu.addtodo.R
import com.phelat.tedu.addtodo.entity.SelectedDate
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.date.di.qualifier.NowDate
import com.phelat.tedu.lifecycle.SingleLiveData
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.todo.constant.TodoConstant
import com.phelat.tedu.todo.entity.TodoEntity
import org.threeten.bp.LocalDate
import java.util.Date
import javax.inject.Inject

class DateViewModel @Inject constructor(
    private val selectedDateReadable: Readable<SelectedDate>,
    private val selectedDateWritable: Writable<SelectedDate>,
    private val dateToLocalDate: Mapper<Date, LocalDate>,
    private val stringResourceProvider: ResourceProvider<StringId, StringResource>,
    @NowDate private val nowDate: Lazy<LocalDate>
) : ViewModel() {

    private val _todoDateObservable = MutableLiveData<String>()
    val todoDateObservable: LiveData<String> = _todoDateObservable

    private val _todoDateSheetObservable = SingleLiveData<Unit>()
    val todoDateSheetObservable: LiveData<Unit> = _todoDateSheetObservable

    fun onBundleReceive(bundle: Bundle?) {
        val todoForEdit = bundle?.getSerializable(TodoConstant.TODO_FOR_EDIT)
        if (todoForEdit is TodoEntity && _todoDateObservable.value == null) {
            val date = dateToLocalDate.mapFirstToSecond(todoForEdit.date)
            onDateSelect(date)
        }
    }

    fun onDateSelect(selectedDate: LocalDate) {
        selectedDateWritable.write(SelectedDate(selectedDate))
        _todoDateObservable.value = when {
            selectedDate == nowDate.value -> {
                stringResourceProvider.getResource(StringId(R.string.addtodo_date_today_text)).resource
            }
            isSelectedDateTomorrow(nowDate.value, selectedDate) -> {
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
        return selectedDateReadable.read().date
    }
}