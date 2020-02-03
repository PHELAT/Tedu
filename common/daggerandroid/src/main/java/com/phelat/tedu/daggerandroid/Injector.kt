package com.phelat.tedu.daggerandroid

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.internal.Preconditions
import kotlin.reflect.KClass

object Injector {

    private const val TAG = "dagger.android.support"
    /**
     * Injects `fragment` if an associated [AndroidInjector] implementation can be found,
     * otherwise throws an [IllegalArgumentException].
     *
     *
     * Uses the following algorithm to find the appropriate `AndroidInjector<Fragment>` to
     * use to inject `fragment`:
     *
     *
     *  1. Walks the parent-fragment hierarchy to find the a fragment that implements [       ], and if none do
     *  1. Uses the `fragment`'s [activity][Fragment.getActivity] if it implements
     * [HasAndroidInjector], and if not
     *  1. Uses the [android.app.Application] if it implements [HasAndroidInjector].
     *
     *
     * If none of them implement [HasAndroidInjector], a [IllegalArgumentException] is
     * thrown.
     *
     * @throws IllegalArgumentException if no parent fragment, activity, or application implements
     * [HasAndroidInjector].
     */
    fun inject(component: KClass<*>, fragment: Fragment) {
        Preconditions.checkNotNull(
            fragment,
            "fragment"
        )
        val hasAndroidInjector = findHasAndroidInjectorForFragment(
            fragment
        )
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(
                TAG, String.format(
                    "An injector for %s was found in %s",
                    fragment.javaClass.canonicalName,
                    hasAndroidInjector.javaClass.canonicalName
                )
            )
        }
        inject(component, fragment, hasAndroidInjector)
    }

    private fun findHasAndroidInjectorForFragment(fragment: Fragment): DispatcherContainer {
        var parentFragment: Fragment? = fragment
        while (parentFragment?.parentFragment?.also { parentFragment = it } != null) {
            if (parentFragment is DispatcherContainer) {
                return parentFragment as DispatcherContainer
            }
        }
        val activity: Activity? = fragment.activity
        if (activity is DispatcherContainer) {
            return activity
        }
        if (activity!!.application is DispatcherContainer) {
            return activity.application as DispatcherContainer
        }
        throw IllegalArgumentException(
            String.format(
                "No injector was found for %s",
                fragment.javaClass.canonicalName
            )
        )
    }

    private fun inject(component: KClass<*>, target: Any, hasAndroidInjector: DispatcherContainer) {
        val androidInjector = hasAndroidInjector.androidInjector(component)
        Preconditions.checkNotNull(
            androidInjector, "%s.androidInjector() returned null", hasAndroidInjector.javaClass
        )
        androidInjector.inject(target)
    }
}