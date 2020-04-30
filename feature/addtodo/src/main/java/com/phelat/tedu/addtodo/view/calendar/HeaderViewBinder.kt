package com.phelat.tedu.addtodo.view.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import org.threeten.bp.LocalDate

class HeaderViewBinder(
    private val nowDate: LocalDate
) : MonthHeaderFooterBinder<HeaderViewHolder> {

    override fun create(view: View): HeaderViewHolder {
        return HeaderViewHolder(view, nowDate)
    }

    override fun bind(container: HeaderViewHolder, month: CalendarMonth) {
        container.bind(month)
    }
}