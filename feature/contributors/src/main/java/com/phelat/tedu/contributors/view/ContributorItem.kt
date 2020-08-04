package com.phelat.tedu.contributors.view

import com.phelat.tedu.contributors.R
import com.phelat.tedu.contributors.entity.ContributorEntity
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contributor.contributionNumber
import kotlinx.android.synthetic.main.item_contributor.contributionTitle
import kotlinx.android.synthetic.main.item_contributor.contributor

class ContributorItem(
    private val entity: ContributorEntity,
    private val onClickListener: (ContributorEntity) -> Unit
) : Item(entity.hashCode().toLong()) {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.contributionNumber.text = entity.contributionNumber
        viewHolder.contributor.text = entity.contributor
        viewHolder.contributionTitle.text = entity.contribution
        viewHolder.itemView.setOnClickListener { onClickListener.invoke(entity) }
    }

    override fun getLayout(): Int = R.layout.item_contributor
}