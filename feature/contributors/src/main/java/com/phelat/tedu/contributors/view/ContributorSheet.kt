package com.phelat.tedu.contributors.view

import android.content.Context
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.Group
import androidx.core.text.buildSpannedString
import androidx.core.text.toSpanned
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.phelat.tedu.contributors.R

class ContributorSheet(
    context: Context
) : BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme) {

    private val bottomSheetTitle: AppCompatTextView

    private val contributorLinkTextView: AppCompatTextView

    private val contributionLinkGroup: Group

    private val contributorLinkGroup: Group

    var sheetTitle: Spanned = buildSpannedString {}
        get() = bottomSheetTitle.text.toSpanned()
        set(value) {
            if (value.isNotEmpty()) {
                bottomSheetTitle.visibility = View.VISIBLE
                bottomSheetTitle.text = value
            }
            field = value
        }

    var onContributionLinkClick: () -> Unit = {}

    var isLinkToContributionVisible: Boolean = true
        get() = contributionLinkGroup.isVisible
        set(value) {
            contributionLinkGroup.isVisible = value
            field = value
        }

    var contributorLinkText: String = ""
        get() = contributorLinkTextView.text.toString()
        set(value) {
            contributorLinkTextView.text = value
            field = value
        }

    var isContributorLinkVisible: Boolean = true
        get() = contributorLinkGroup.isVisible
        set(value) {
            contributorLinkGroup.isVisible = value
            field = value
        }

    var onContributorLinkClick: () -> Unit = {}

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.view_contributor_detail_sheet, null, false)
        bottomSheetTitle = view.findViewById(R.id.sheetTitle)
        contributionLinkGroup = view.findViewById(R.id.contributionLinkGroup)
        contributorLinkTextView = view.findViewById(R.id.contributorLinkTitle)
        contributorLinkGroup = view.findViewById(R.id.contributorLinkGroup)
        view.findViewById<View>(R.id.contributionLinkClick).setOnClickListener {
            onContributionLinkClick.invoke()
            dismiss()
        }
        view.findViewById<View>(R.id.contributorLinkClick).setOnClickListener {
            onContributorLinkClick.invoke()
            dismiss()
        }
        setContentView(view)
    }
}