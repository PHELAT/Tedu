package com.phelat.tedu.contributors.view

import com.phelat.tedu.contributors.R
import com.phelat.tedu.contributors.entity.ContributorEntity
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contributor.contributionTitle

class ContributorItem(
    private val entity: ContributorEntity
) : Item(entity.contributionNumber) {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.contributionTitle.text = entity.contribution
    }

    override fun getLayout(): Int = R.layout.item_contributor
}