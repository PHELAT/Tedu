package com.phelat.tedu.contributors.view

import android.view.View
import com.phelat.tedu.contributors.R
import com.phelat.tedu.contributors.databinding.ItemContributorBinding
import com.phelat.tedu.contributors.entity.ContributorEntity
import com.xwray.groupie.viewbinding.BindableItem

class ContributorItem(
    private val entity: ContributorEntity,
    private val onClickListener: (ContributorEntity) -> Unit
) : BindableItem<ItemContributorBinding>(entity.hashCode().toLong()) {

    override fun bind(viewBinding: ItemContributorBinding, position: Int) {
        viewBinding.contributionNumber.text = entity.contributionNumber
        viewBinding.contributor.text = entity.contributor
        viewBinding.contributionTitle.text = entity.contribution
        viewBinding.root.setOnClickListener { onClickListener.invoke(entity) }
    }

    override fun initializeViewBinding(view: View): ItemContributorBinding {
        return ItemContributorBinding.bind(view)
    }

    override fun getLayout(): Int = R.layout.item_contributor
}