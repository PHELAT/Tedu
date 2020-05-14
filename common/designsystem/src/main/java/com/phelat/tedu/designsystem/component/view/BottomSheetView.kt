package com.phelat.tedu.designsystem.component.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.phelat.tedu.designsystem.R
import com.phelat.tedu.designsystem.entity.BottomSheetItemEntity

class BottomSheetView(
    context: Context
) : BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme) {

    private val bottomSheetTitle: AppCompatTextView

    private val bottomSheetRecycler: RecyclerView

    var dismissOnClick: Boolean = true

    var sheetTitle: String = ""
        set(value) {
            if (value.isNotEmpty()) {
                bottomSheetTitle.visibility = View.VISIBLE
                bottomSheetTitle.text = value
            }
            field = value
        }

    var sheetItems: List<BottomSheetItemEntity> = emptyList()
        set(value) {
            val adapter = BottomSheetAdapter(value) {
                if (dismissOnClick) {
                    dismiss()
                }
            }
            bottomSheetRecycler.adapter = adapter
            field = value
        }

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.view_bottom_sheet, null, false)
        bottomSheetRecycler = view.findViewById(R.id.bottomSheetRecycler)
        bottomSheetRecycler.layoutManager = LinearLayoutManager(context)
        bottomSheetTitle = view.findViewById(R.id.sheetTitle)
        setContentView(view)
    }
}