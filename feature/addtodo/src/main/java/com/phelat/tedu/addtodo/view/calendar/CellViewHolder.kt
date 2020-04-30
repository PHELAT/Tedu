package com.phelat.tedu.addtodo.view.calendar

import android.graphics.Paint
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import com.phelat.tedu.addtodo.R
import kotlinx.android.synthetic.main.item_calendar_cell.view.cellText
import org.threeten.bp.LocalDate

class CellViewHolder(view: View, private val selectedDate: () -> LocalDate) : ViewContainer(view) {

    private val cellText: AppCompatTextView = view.cellText

    fun bind(
        day: CalendarDay,
        today: LocalDate,
        onClickListener: () -> Unit
    ) {
        if (day.owner == DayOwner.THIS_MONTH) {
            cellText.visibility = View.VISIBLE
            cellText.text = day.date.dayOfMonth.toString()
            bindRegardingToDateInformation(day, today)
        } else {
            cellText.visibility = View.GONE
        }
        cellText.setOnClickListener {
            onClickListener.invoke()
        }
    }

    private fun bindRegardingToDateInformation(day: CalendarDay, today: LocalDate) {
        when {
            day.date == selectedDate() -> {
                with(cellText) {
                    setBackgroundResource(R.drawable.shape_calendar_selected_date)
                    setTextColor(ContextCompat.getColor(context, R.color.text_primary_revert_color))
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
            day.date == today -> {
                with(cellText) {
                    setBackgroundResource(R.drawable.selector_calendar_today)
                    setTextColor(ContextCompat.getColor(context, R.color.text_secondary_color))
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
            day.date.isBefore(today) -> {
                with(cellText) {
                    background = null
                    setTextColor(ContextCompat.getColor(context, R.color.text_hint_color))
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            }
            else -> {
                with(cellText) {
                    setBackgroundResource(R.drawable.selector_click_oval)
                    setTextColor(ContextCompat.getColor(context, R.color.text_secondary_color))
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
        }
    }
}