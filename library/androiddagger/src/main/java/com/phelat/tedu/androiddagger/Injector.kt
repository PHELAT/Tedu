package com.phelat.tedu.androiddagger

import android.app.Activity
import androidx.fragment.app.Fragment
import java.util.Locale

inline fun <reified Component : DispatcherComponent> Fragment.inject() {
    var dispatcherContainer: DispatcherContainer? = null
    var parentFragment: Fragment? = this
    while (parentFragment?.parentFragment?.also { parentFragment = it } != null) {
        if (parentFragment is DispatcherContainer) {
            dispatcherContainer = parentFragment as DispatcherContainer
        }
    }
    if (null == dispatcherContainer) {
        val activity: Activity? = activity
        if (activity is DispatcherContainer) {
            dispatcherContainer = activity
        }
    }
    if (null == dispatcherContainer && requireActivity().application is DispatcherContainer) {
        dispatcherContainer = requireActivity().application as DispatcherContainer
    }
    if (null == dispatcherContainer) {
        throw IllegalArgumentException(
            String.format(
                Locale.ENGLISH,
                "No injector was found for %s",
                this.javaClass.canonicalName
            )
        )
    }
    val androidInjector = dispatcherContainer.androidInjector(Component::class)
    androidInjector.inject(this)
}