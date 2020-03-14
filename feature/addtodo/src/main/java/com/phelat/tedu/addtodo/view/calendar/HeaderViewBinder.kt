package com.phelat.tedu.addtodo.view.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder

class HeaderViewBinder : MonthHeaderFooterBinder<HeaderViewHolder> {

    override fun create(view: View): HeaderViewHolder {
        return HeaderViewHolder(view)
    }

    override fun bind(container: HeaderViewHolder, month: CalendarMonth) {
        container.bind(month)
    }
}