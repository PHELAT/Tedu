package com.phelat.tedu.addtodo.view.calendar

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.phelat.tedu.addtodo.R
import com.phelat.tedu.addtodo.view.TextStyle

data class CalendarCellViewState(
    val isCellTextVisible: Boolean = false,
    val cellText: String = "",
    @DrawableRes val cellBackground: Int? = null,
    @ColorRes val cellTextColor: Int = R.color.text_secondary_color,
    val cellTextStyle: TextStyle = TextStyle.Normal
)