package com.phelat.tedu.addtodo.view.calendar

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.ViewContainer
import com.phelat.tedu.addtodo.viewmodel.DateViewModel
import kotlinx.android.synthetic.main.item_calendar_header.view.headerMonth
import kotlinx.android.synthetic.main.item_calendar_header.view.headerYear

class HeaderViewHolder(view: View, private val dateViewModel: DateViewModel) : ViewContainer(view) {

    private val headerYear: AppCompatTextView = view.headerYear
    private val headerMonth: AppCompatTextView = view.headerMonth

    fun bind(month: CalendarMonth) {
        dateViewModel.onBindCalendarHeader(month).apply {
            headerMonth.text = headerMonthText
            headerYear.isVisible = isHeaderYearTextVisible
            headerYear.text = headerYearText
        }
    }
}