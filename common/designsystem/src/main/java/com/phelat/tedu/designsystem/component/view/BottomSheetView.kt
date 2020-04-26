package com.phelat.tedu.designsystem.component.view

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.phelat.tedu.designsystem.R
import com.phelat.tedu.designsystem.entity.BottomSheetItemEntity

class BottomSheetView(
    context: Context,
    private val dismissOnClick: Boolean = true
) : BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme) {

    private val bottomSheetRecycler: RecyclerView

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.view_bottom_sheet, null, false)
        bottomSheetRecycler = view.findViewById(R.id.bottomSheetRecycler)
        bottomSheetRecycler.layoutManager = LinearLayoutManager(context)
        setContentView(view)
    }

    fun setItems(items: List<BottomSheetItemEntity>): BottomSheetDialog {
        val adapter = BottomSheetAdapter(items) {
            if (dismissOnClick) {
                dismiss()
            }
        }
        bottomSheetRecycler.adapter = adapter
        return this
    }
}