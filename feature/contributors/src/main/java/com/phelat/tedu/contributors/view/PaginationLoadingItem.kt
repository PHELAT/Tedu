package com.phelat.tedu.contributors.view

import android.view.View
import com.phelat.tedu.contributors.R
import com.phelat.tedu.contributors.databinding.ItemPaginationLoadingBinding
import com.xwray.groupie.viewbinding.BindableItem

class PaginationLoadingItem : BindableItem<ItemPaginationLoadingBinding>() {

    override fun bind(viewHolder: ItemPaginationLoadingBinding, position: Int) {
        // No-op!
    }

    override fun initializeViewBinding(view: View): ItemPaginationLoadingBinding {
        return ItemPaginationLoadingBinding.bind(view)
    }

    override fun getLayout(): Int = R.layout.item_pagination_loading
}