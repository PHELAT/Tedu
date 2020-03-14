package com.phelat.tedu.addtodo.view.calendar

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import com.phelat.tedu.addtodo.R
import kotlinx.android.synthetic.main.item_calendar_cell.view.cellText
import org.threeten.bp.LocalDate

class CellViewHolder(view: View) : ViewContainer(view) {

    private val cellText: AppCompatTextView = view.cellText

    fun bind(
        day: CalendarDay,
        today: LocalDate,
        selectedDate: LocalDate,
        onClickListener: () -> Unit
    ) {
        if (day.owner == DayOwner.THIS_MONTH) {
            cellText.visibility = View.VISIBLE
            cellText.text = day.date.dayOfMonth.toString()
            when {
                day.date == selectedDate -> {
                    cellText.setBackgroundResource(R.drawable.shape_calendar_selected_date)
                    cellText.setTextColor(
                        ContextCompat.getColor(
                            cellText.context,
                            R.color.text_primary_revert_color
                        )
                    )
                }
                day.date == today -> {
                    cellText.setBackgroundResource(R.drawable.selector_calendar_today)
                    cellText.setTextColor(
                        ContextCompat.getColor(
                            cellText.context,
                            R.color.text_secondary_color
                        )
                    )
                }
                day.date.isBefore(today) -> {
                    cellText.background = null
                    cellText.setTextColor(
                        ContextCompat.getColor(
                            cellText.context,
                            R.color.text_hint_color
                        )
                    )
                }
                else -> {
                    cellText.setBackgroundResource(R.drawable.selector_click_oval)
                    cellText.setTextColor(
                        ContextCompat.getColor(
                            cellText.context,
                            R.color.text_secondary_color
                        )
                    )
                }
            }
        } else {
            cellText.visibility = View.GONE
        }
        cellText.setOnClickListener {
            onClickListener.invoke()
        }
    }
}