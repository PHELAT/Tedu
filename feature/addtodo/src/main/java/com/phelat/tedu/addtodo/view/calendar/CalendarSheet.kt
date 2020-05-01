package com.phelat.tedu.addtodo.view.calendar

import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kizitonwose.calendarview.CalendarView
import com.phelat.tedu.addtodo.R
import com.phelat.tedu.addtodo.viewmodel.DateViewModel
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.WeekFields
import java.util.Locale

class CalendarSheet(
    context: Context,
    dateViewModel: DateViewModel,
    lifecycleOwner: LifecycleOwner
) : BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme) {

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.view_bottom_sheet_calendar, null, false)
        val calendarView: CalendarView = view.findViewById(R.id.calendarView)
        calendarView.dayBinder = CellViewBinder(dateViewModel)
        calendarView.monthHeaderBinder = HeaderViewBinder(dateViewModel)
        val currentMonth = YearMonth.now()
        val lastMonth = currentMonth.plusMonths(MONTHS_IN_A_YEAR - 1)
        val firstDayOfWeek = WeekFields.of(Locale.ENGLISH).firstDayOfWeek
        calendarView.setup(currentMonth, lastMonth, firstDayOfWeek)
        setContentView(view)
        dateViewModel.dateChangeObservable.observe(lifecycleOwner) { changedDate ->
            calendarView.notifyDateChanged(changedDate)
        }
    }

    companion object {
        private const val MONTHS_IN_A_YEAR = 12L
    }
}