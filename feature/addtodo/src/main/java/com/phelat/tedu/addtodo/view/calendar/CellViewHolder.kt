package com.phelat.tedu.addtodo.view.calendar

import android.graphics.Paint
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.ViewContainer
import com.phelat.tedu.addtodo.databinding.ItemCalendarCellBinding
import com.phelat.tedu.addtodo.view.TextStyle
import com.phelat.tedu.addtodo.viewmodel.DateViewModel

class CellViewHolder(
    binding: ItemCalendarCellBinding,
    private val dateViewModel: DateViewModel
) : ViewContainer(binding.root) {

    private val cellTextView: AppCompatTextView = binding.cellTextView

    fun bind(day: CalendarDay) {
        dateViewModel.onBindCalendarCell(day).apply {
            with(cellTextView) {
                isVisible = isCellTextVisible
                text = cellText
                setTextColor(ContextCompat.getColor(context, cellTextColor))
                if (cellBackground != null) {
                    setBackgroundResource(cellBackground)
                } else {
                    background = null
                }
                paintFlags = when (cellTextStyle) {
                    is TextStyle.Normal -> paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    is TextStyle.StrikeThrough -> paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            }
        }
        cellTextView.setOnClickListener {
            dateViewModel.onDateSelect(day)
        }
    }
}