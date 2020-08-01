package com.phelat.tedu.contributors.view

import com.phelat.tedu.contributors.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

class PaginationLoadingItem : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        // No-op!
    }

    override fun getLayout(): Int = R.layout.item_pagination_loading
}