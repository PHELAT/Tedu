package com.phelat.tedu.sdkextensions

import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay

private const val INPUT_FOCUS_DELAY_IN_MILLIS = 64L

fun Fragment.showKeyboard(inputToFocus: EditText) {
    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    lifecycleScope.launchWhenResumed {
        delay(INPUT_FOCUS_DELAY_IN_MILLIS)
        inputToFocus.requestFocus()
    }
}

fun Fragment.hideKeyboard(windowToken: IBinder) {
    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, 0)
}