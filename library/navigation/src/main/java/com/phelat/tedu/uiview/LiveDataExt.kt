package com.phelat.tedu.uiview

import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

fun <T : Navigate> LiveData<T>.observeNavigation(fragment: Fragment) {
    observe(fragment.viewLifecycleOwner, Observer {
        when (it) {
            is Navigate.ToDirection -> {
                fragment.findNavController().navigate(it.directionId.id, it.bundle)
            }
            is Navigate.ToDeepLink -> {
                fragment.findNavController().navigate(it.deepLink.toUri())
            }
            is Navigate.Up -> {
                fragment.findNavController().navigateUp()
            }
            is Navigate.Recreate -> {
                fragment.requireActivity().recreate()
            }
        }
    })
}