package com.phelat.tedu.designsystem.component.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phelat.tedu.designsystem.R
import com.phelat.tedu.designsystem.entity.BottomSheetItemEntity

internal class BottomSheetAdapter(
    private val items: List<BottomSheetItemEntity>,
    private val onClick: () -> Unit
) : RecyclerView.Adapter<BottomSheetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bottom_sheet, parent, false)
        return BottomSheetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }
}