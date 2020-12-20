package com.phelat.tedu.plaugin

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class PlauginActivity : AppCompatActivity {

    constructor() : super()

    constructor(@LayoutRes layout: Int) : super(layout)

    private val activityPlugins by lazy(LazyThreadSafetyMode.NONE) { plugins() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPlugins.forEach { plugin ->
            plugin.onCreate(savedInstanceState)
        }
    }

    open fun plugins(): MutableList<ActivityPlugin> = mutableListOf()
}