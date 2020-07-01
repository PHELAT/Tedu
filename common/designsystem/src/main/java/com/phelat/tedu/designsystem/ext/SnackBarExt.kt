package com.phelat.tedu.designsystem.ext

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.phelat.tedu.designsystem.R

fun Activity.makeLongSnackBar(message: String): Snackbar {
    return Snackbar.make(findViewById<View>(R.id.activityRoot), message, Snackbar.LENGTH_LONG)
        .apply { anchorView = findViewById(R.id.bottomNavigationView) }
}