package com.phelat.tedu.designsystem.component.view

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.phelat.tedu.designsystem.databinding.ItemBottomSheetBinding
import com.phelat.tedu.designsystem.entity.BottomSheetItemEntity

internal class BottomSheetViewHolder(
    binding: ItemBottomSheetBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val itemIcon: AppCompatImageView = binding.itemIcon
    private val itemTitle: AppCompatTextView = binding.itemTitle

    fun bind(item: BottomSheetItemEntity, onClick: () -> Unit) {
        if (item.itemIconResource != null) {
            itemIcon.setImageResource(item.itemIconResource)
            itemIcon.visibility = View.VISIBLE
        } else {
            itemIcon.visibility = View.GONE
        }
        itemTitle.setText(item.itemTitleResource)
        itemView.setOnClickListener {
            onClick.invoke()
            item.itemOnClickListener.invoke()
        }
    }
}