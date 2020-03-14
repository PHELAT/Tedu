package com.phelat.tedu.addtodo.view.calendar

import android.view.View
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import org.threeten.bp.LocalDate

class CellViewBinder(
    private val notifyDateChange: (date: LocalDate) -> Unit,
    private val onSelectNewDate: (date: LocalDate) -> Unit
) : DayBinder<CellViewHolder> {

    private val today = LocalDate.now()

    private var selectedDate: LocalDate = LocalDate.of(today.year, today.month, today.dayOfMonth)

    override fun bind(container: CellViewHolder, day: CalendarDay) {
        container.bind(day, today, selectedDate) {
            if (day.owner == DayOwner.THIS_MONTH && day.date.isBefore(today).not()) {
                val previousSelectedDate = LocalDate.of(
                    selectedDate.year,
                    selectedDate.month,
                    selectedDate.dayOfMonth
                )
                selectedDate = day.date
                notifyDateChange.invoke(previousSelectedDate)
                notifyDateChange.invoke(selectedDate)
                onSelectNewDate.invoke(selectedDate)
            }
        }
    }

    override fun create(view: View): CellViewHolder {
        return CellViewHolder(view)
    }
}