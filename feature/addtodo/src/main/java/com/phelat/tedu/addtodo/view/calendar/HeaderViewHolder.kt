package com.phelat.tedu.addtodo.view.calendar

import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.ViewContainer
import com.phelat.tedu.addtodo.databinding.ItemCalendarHeaderBinding
import com.phelat.tedu.addtodo.viewmodel.DateViewModel

class HeaderViewHolder(
    binding: ItemCalendarHeaderBinding,
    private val dateViewModel: DateViewModel
) : ViewContainer(binding.root) {

    private val headerYear: AppCompatTextView = binding.headerYear
    private val headerMonth: AppCompatTextView = binding.headerMonth

    fun bind(month: CalendarMonth) {
        dateViewModel.onBindCalendarHeader(month).apply {
            headerMonth.text = headerMonthText
            headerYear.isVisible = isHeaderYearTextVisible
            headerYear.text = headerYearText
        }
    }
}