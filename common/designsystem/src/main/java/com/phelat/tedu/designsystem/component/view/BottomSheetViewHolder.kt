package com.phelat.tedu.designsystem.component.view

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.phelat.tedu.designsystem.entity.BottomSheetItemEntity
import kotlinx.android.synthetic.main.item_bottom_sheet.view.itemIcon
import kotlinx.android.synthetic.main.item_bottom_sheet.view.itemTitle

internal class BottomSheetViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val itemIcon: AppCompatImageView = view.itemIcon
    private val itemTitle: AppCompatTextView = view.itemTitle

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