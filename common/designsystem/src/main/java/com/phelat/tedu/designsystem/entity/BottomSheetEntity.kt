package com.phelat.tedu.designsystem.entity

data class BottomSheetEntity(
    val items: List<BottomSheetItemEntity>,
    val sheetTitle: String = "",
    val dismissOnClick: Boolean = true
)