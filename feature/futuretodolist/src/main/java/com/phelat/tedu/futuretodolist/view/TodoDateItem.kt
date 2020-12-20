package com.phelat.tedu.futuretodolist.view

import com.phelat.tedu.futuretodolist.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_date_header.dateTitleTextView

class TodoDateItem(private val date: String) : Item(Long.MAX_VALUE) {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.dateTitleTextView.text = date
    }

    override fun getLayout(): Int {
        return R.layout.item_date_header
    }
}