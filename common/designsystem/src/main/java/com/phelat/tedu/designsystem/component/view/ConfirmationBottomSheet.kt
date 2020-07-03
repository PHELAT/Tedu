package com.phelat.tedu.designsystem.component.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.phelat.tedu.designsystem.R

class ConfirmationBottomSheet(
    context: Context
) : BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme) {

    private val bottomSheetTitle: AppCompatTextView

    private val okayButton: AppCompatTextView
    private val cancelButton: AppCompatTextView

    var sheetTitle: String = ""
        set(value) {
            if (value.isNotEmpty()) {
                bottomSheetTitle.visibility = View.VISIBLE
                bottomSheetTitle.text = value
            }
            field = value
        }

    var onOkayButtonClick: () -> Unit = {}
    var onCancelButtonClick: () -> Unit = {}

    var dismissOnClick: Boolean = true

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.view_confirmation_bottom_sheet, null, false)
        bottomSheetTitle = view.findViewById(R.id.sheetTitle)
        okayButton = view.findViewById(R.id.confirmationOkay)
        okayButton.setOnClickListener {
            dismissIfNecessary()
            onOkayButtonClick.invoke()
        }
        cancelButton = view.findViewById(R.id.confirmationCancel)
        cancelButton.setOnClickListener {
            dismissIfNecessary()
            onCancelButtonClick.invoke()
        }
        setContentView(view)
    }

    private fun dismissIfNecessary() {
        if (dismissOnClick) {
            dismiss()
        }
    }
}