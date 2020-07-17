package com.phelat.tedu.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.interModuleNavigate(uri: Uri, navOptions: NavOptions = defaultNavOptions()) {
    navigate(uri, navOptions)
}

private fun defaultNavOptions(): NavOptions {
    return NavOptions.Builder()
        .setEnterAnim(R.anim.nav_default_enter_anim)
        .setExitAnim(R.anim.nav_default_exit_anim)
        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
        .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
        .build()
}