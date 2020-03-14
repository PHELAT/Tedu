package com.phelat.tedu.addtodo.view.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import org.threeten.bp.LocalDate

class CellViewBinder(
    private val selectedDate: () -> LocalDate,
    private val notifyDateChange: (date: LocalDate) -> Unit,
    private val onSelectNewDate: (date: LocalDate) -> Unit
) : DayBinder<CellViewHolder> {

    private val today = LocalDate.now()

    override fun bind(container: CellViewHolder, day: CalendarDay) {
        container.bind(day, today) {
            if (day.owner == DayOwner.THIS_MONTH && day.date.isBefore(today).not()) {
                val previousSelectedDate = selectedDate()
                onSelectNewDate.invoke(day.date)
                notifyDateChange.invoke(previousSelectedDate)
                notifyDateChange.invoke(selectedDate())
            }
        }
    }

    override fun create(view: View): CellViewHolder {
        return CellViewHolder(view, selectedDate)
    }
}