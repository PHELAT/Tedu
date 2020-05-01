package com.phelat.tedu.addtodo.view.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.phelat.tedu.addtodo.viewmodel.DateViewModel

class CellViewBinder(private val dateViewModel: DateViewModel) : DayBinder<CellViewHolder> {

    override fun bind(container: CellViewHolder, day: CalendarDay) {
        container.bind(day)
    }

    override fun create(view: View): CellViewHolder {
        return CellViewHolder(view, dateViewModel)
    }
}