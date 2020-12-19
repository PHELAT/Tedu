package com.phelat.tedu.plugins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.phelat.tedu.plaugin.FragmentPlugin

fun <T : ViewBinding> Fragment.viewBinding(block: () -> T): Pair<FragmentPlugin, () -> T?> {
    var binding: T? = null
    val bindingFunction: () -> T? = { binding }
    return object : FragmentPlugin {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = block.invoke()
            return requireNotNull(binding).root
        }

        override fun onDestroyView() {
            super.onDestroyView()
            binding = null
        }
    } to bindingFunction
}

val <T : ViewBinding> Pair<FragmentPlugin, () -> T?>.plugin: FragmentPlugin
    get() = first

val <T : ViewBinding> Pair<FragmentPlugin, () -> T?>.binding: T
    get() = requireNotNull(second.invoke())

operator fun <T : ViewBinding> Pair<FragmentPlugin, () -> T?>.invoke(): T {
    return requireNotNull(second.invoke())
}