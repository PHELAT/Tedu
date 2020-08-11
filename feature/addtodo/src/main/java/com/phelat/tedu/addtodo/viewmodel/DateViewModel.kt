package com.phelat.tedu.addtodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.phelat.tedu.addtodo.R
import com.phelat.tedu.addtodo.di.scope.AddTodoScope
import com.phelat.tedu.addtodo.entity.SelectedDate
import com.phelat.tedu.addtodo.view.TextStyle
import com.phelat.tedu.addtodo.view.calendar.CalendarCellViewState
import com.phelat.tedu.addtodo.view.calendar.CalendarHeaderViewState
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.date.di.qualifier.NowDate
import com.phelat.tedu.lifecycle.SingleLiveData
import com.phelat.tedu.mapper.Mapper
import com.phelat.tedu.sdkextensions.Visibility
import com.phelat.tedu.todo.entity.TodoEntity
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle.FULL
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AddTodoScope
class DateViewModel @Inject constructor(
    private val selectedDateReadable: Readable<SelectedDate>,
    private val selectedDateWritable: Writable<SelectedDate>,
    private val dateToLocalDate: Mapper<Date, LocalDate>,
    private val stringResourceProvider: ResourceProvider<StringId, StringResource>,
    @NowDate private val nowDate: Lazy<LocalDate>,
    private val localDateToTeduDate: Mapper<LocalDate, TeduDate>
) : ViewModel() {

    private val _todoDateObservable = MutableLiveData<String>()
    val todoDateObservable: LiveData<String> = _todoDateObservable

    private val _todoDateSheetObservable = SingleLiveData<Visibility>()
    val todoDateSheetObservable: LiveData<Visibility> = _todoDateSheetObservable

    private val _dateChangeObservable = SingleLiveData<LocalDate>()
    val dateChangeObservable: LiveData<LocalDate> = _dateChangeObservable

    fun onFragmentArgumentReceived(fragmentArgument: TodoEntity) {
        if (_todoDateObservable.value == null) {
            val date = dateToLocalDate.mapFirstToSecond(fragmentArgument.date)
            updateSelectedDate(date)
        }
    }

    fun onDateSelect(selectedDay: CalendarDay) {
        if (selectedDay.owner != DayOwner.THIS_MONTH) return
        if (selectedDay.date.isBefore(nowDate.value)) return
        val selectedDate = selectedDay.date
        updateSelectedDate(selectedDate)
        _todoDateSheetObservable.value = Visibility.InVisible
    }

    private fun updateSelectedDate(selectedDate: LocalDate) {
        val previousSelectedDate = selectedDateReadable.read().date
        selectedDateWritable.write(SelectedDate(selectedDate))
        _dateChangeObservable.value = previousSelectedDate
        _dateChangeObservable.value = selectedDateReadable.read().date

        val teduDate = localDateToTeduDate.mapFirstToSecond(selectedDate)
        _todoDateObservable.value = when (teduDate) {
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
    }

    fun onSelectDateClick() {
        _todoDateSheetObservable.value = Visibility.Visible
    }

    fun onBindCalendarCell(day: CalendarDay): CalendarCellViewState {
        return if (day.owner == DayOwner.THIS_MONTH) {
            when {
                day.date == selectedDateReadable.read().date -> {
                    CalendarCellViewState(
                        isCellTextVisible = true,
                        cellText = day.date.dayOfMonth.toString(),
                        cellBackground = R.drawable.shape_calendar_selected_date,
                        cellTextColor = R.color.text_primary_revert_color,
                        cellTextStyle = TextStyle.Normal
                    )
                }
                day.date == nowDate.value -> {
                    CalendarCellViewState(
                        isCellTextVisible = true,
                        cellText = day.date.dayOfMonth.toString(),
                        cellBackground = R.drawable.selector_calendar_today,
                        cellTextColor = R.color.text_secondary_color,
                        cellTextStyle = TextStyle.Normal
                    )
                }
                day.date.isBefore(nowDate.value) -> {
                    CalendarCellViewState(
                        isCellTextVisible = true,
                        cellText = day.date.dayOfMonth.toString(),
                        cellBackground = null,
                        cellTextColor = R.color.text_hint_color,
                        cellTextStyle = TextStyle.StrikeThrough
                    )
                }
                else -> {
                    CalendarCellViewState(
                        isCellTextVisible = true,
                        cellText = day.date.dayOfMonth.toString(),
                        cellBackground = R.drawable.selector_click_oval,
                        cellTextColor = R.color.text_secondary_color,
                        cellTextStyle = TextStyle.Normal
                    )
                }
            }
        } else {
            CalendarCellViewState(isCellTextVisible = false)
        }
    }

    fun onBindCalendarHeader(month: CalendarMonth): CalendarHeaderViewState {
        return if (nowDate.value.year != month.year) {
            CalendarHeaderViewState(
                headerMonthText = month.yearMonth.month.getDisplayName(FULL, Locale.ENGLISH),
                isHeaderYearTextVisible = true,
                headerYearText = month.year.toString()
            )
        } else {
            CalendarHeaderViewState(
                headerMonthText = month.yearMonth.month.getDisplayName(FULL, Locale.ENGLISH),
                isHeaderYearTextVisible = false
            )
        }
    }
}