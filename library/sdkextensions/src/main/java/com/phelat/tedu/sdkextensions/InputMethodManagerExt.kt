package com.phelat.tedu.sdkextensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment

fun Fragment.showKeyboard(inputToFocus: EditText) {
    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    inputToFocus.requestFocus()
}