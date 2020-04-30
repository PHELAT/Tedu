package com.phelat.tedu.addtodo.view.calendar

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.item_calendar_header.view.headerMonth
import kotlinx.android.synthetic.main.item_calendar_header.view.headerYear
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.util.Locale

class HeaderViewHolder(view: View, private val nowDate: LocalDate) : ViewContainer(view) {

    private val headerYear: AppCompatTextView = view.headerYear
    private val headerMonth: AppCompatTextView = view.headerMonth

    fun bind(month: CalendarMonth) {
        headerMonth.text = month.yearMonth.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        if (nowDate.year != month.year) {
            headerYear.visibility = View.VISIBLE
            headerYear.text = month.year.toString()
        } else {
            headerYear.visibility = View.GONE
        }
    }
}