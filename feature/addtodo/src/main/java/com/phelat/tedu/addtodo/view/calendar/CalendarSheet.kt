package com.phelat.tedu.addtodo.view.calendar

import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kizitonwose.calendarview.CalendarView
import com.phelat.tedu.addtodo.R
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields
import java.util.Locale

class CalendarSheet(
    context: Context,
    onDateSelect: (LocalDate) -> Unit,
    selectedDate: () -> LocalDate,
    nowDate: LocalDate
) : BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme) {

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.view_bottom_sheet_calendar, null, false)
        val calendarView: CalendarView = view.findViewById(R.id.calendarView)
        calendarView.dayBinder = CellViewBinder(
            notifyDateChange = { changedDate ->
                calendarView.notifyDateChanged(changedDate)
            },
            onSelectNewDate = { newDate ->
                dismiss()
                onDateSelect.invoke(newDate)
            },
            selectedDate = selectedDate
        )
        calendarView.monthHeaderBinder = HeaderViewBinder(nowDate)
        val currentMonth = YearMonth.now()
        val lastMonth = currentMonth.plusMonths(MONTHS_IN_A_YEAR - 1)
        val firstDayOfWeek = WeekFields.of(Locale.ENGLISH).firstDayOfWeek
        calendarView.setup(currentMonth, lastMonth, firstDayOfWeek)
        setContentView(view)
    }

    companion object {
        private const val MONTHS_IN_A_YEAR = 12L
    }
}