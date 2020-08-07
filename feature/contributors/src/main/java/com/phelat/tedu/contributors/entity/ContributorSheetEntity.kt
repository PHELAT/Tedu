package com.phelat.tedu.contributors.entity

import android.text.Spanned

data class ContributorSheetEntity(
    val sheetTitle: Spanned,
    val isLinkToContributionVisible: Boolean,
    val isLinkToContributorVisible: Boolean,
    val linkToContributorText: String
)