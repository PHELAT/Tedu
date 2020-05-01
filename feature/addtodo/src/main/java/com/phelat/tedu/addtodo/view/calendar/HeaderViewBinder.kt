package com.phelat.tedu.addtodo.view.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.phelat.tedu.addtodo.viewmodel.DateViewModel

class HeaderViewBinder(
    private val dateViewModel: DateViewModel
) : MonthHeaderFooterBinder<HeaderViewHolder> {

    override fun create(view: View): HeaderViewHolder {
        return HeaderViewHolder(view, dateViewModel)
    }

    override fun bind(container: HeaderViewHolder, month: CalendarMonth) {
        container.bind(month)
    }
}