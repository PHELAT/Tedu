package com.phelat.tedu.navigation

import android.net.Uri
import android.os.Parcelable
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import java.io.Serializable

fun NavController.interModuleNavigate(
    link: Uri,
    navOptions: NavOptions = defaultNavOptions
) {
    interModuleNavigate(link, null, { _, _ -> }, navOptions)
}

fun NavController.interModuleNavigate(
    link: Uri,
    serializableData: Serializable?,
    navOptions: NavOptions = defaultNavOptions
) {
    interModuleNavigate(link, serializableData, ExtraDataDataSource::storeExtraData, navOptions)
}

fun NavController.interModuleNavigate(
    link: Uri,
    parcelableData: Parcelable?,
    navOptions: NavOptions = defaultNavOptions
) {
    interModuleNavigate(link, parcelableData, ExtraDataDataSource::storeExtraData, navOptions)
}

private fun <T : Any> NavController.interModuleNavigate(
    link: Uri,
    data: T?,
    storeData: (sign: String, data: T) -> Unit,
    navOptions: NavOptions
) {
    val signedLink = if (data != null) {
        val sign = System.currentTimeMillis().toString()
        storeData.invoke(sign, data)
        link.buildUpon()
            .encodedQuery("${ExtraDataDataSource.INTER_MODULE_NAVIGATION_EXTRA_DATA}=$sign")
            .build()
    } else {
        link
    }
    navigate(signedLink, navOptions)
}

private val defaultNavOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.nav_default_enter_anim)
    .setExitAnim(R.anim.nav_default_exit_anim)
    .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
    .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
    .build()