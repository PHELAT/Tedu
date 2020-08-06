package com.phelat.tedu.contributors.view

import android.content.Context
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.buildSpannedString
import androidx.core.text.toSpanned
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.phelat.tedu.contributors.R

class ContributorSheet(
    context: Context
) : BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme) {

    private val bottomSheetTitle: AppCompatTextView

    var sheetTitle: Spanned = buildSpannedString {}
        get() = bottomSheetTitle.text.toSpanned()
        set(value) {
            if (value.isNotEmpty()) {
                bottomSheetTitle.visibility = View.VISIBLE
                bottomSheetTitle.text = value
            }
            field = value
        }

    var onContributorLinkClick: () -> Unit = {}

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.view_contributor_detail_sheet, null, false)
        bottomSheetTitle = view.findViewById(R.id.sheetTitle)
        view.findViewById<View>(R.id.contributionLinkClick).setOnClickListener {
            onContributorLinkClick.invoke()
            dismiss()
        }
        setContentView(view)
    }
}