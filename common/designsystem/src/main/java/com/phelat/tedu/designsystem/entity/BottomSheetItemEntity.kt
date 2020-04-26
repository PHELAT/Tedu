package com.phelat.tedu.designsystem.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class BottomSheetItemEntity(
    @DrawableRes val itemIconResource: Int?,
    @StringRes val itemTitleResource: Int,
    val itemOnClickListener: () -> Unit
)