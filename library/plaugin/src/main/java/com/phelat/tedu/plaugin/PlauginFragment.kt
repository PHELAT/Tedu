package com.phelat.tedu.plaugin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class PlauginFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes layout: Int) : super(layout)

    private val fragmentPlugins by lazy(LazyThreadSafetyMode.NONE) { plugins() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentPlugins.forEach { plugin ->
            plugin.onAttach(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentPlugins.forEach { plugin ->
            plugin.onCreate(savedInstanceState)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPlugins.forEach { plugin ->
            val createdView = plugin.onCreateView(inflater, container, savedInstanceState)
            if (createdView != null) {
                return createdView
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentPlugins.forEach { plugin ->
            plugin.onDestroyView()
        }
    }

    open fun plugins(): MutableList<FragmentPlugin> = mutableListOf()
}