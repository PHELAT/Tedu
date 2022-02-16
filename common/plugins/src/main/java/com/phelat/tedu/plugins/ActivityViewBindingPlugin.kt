package com.phelat.tedu.plugins

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.phelat.tedu.plaugin.ActivityPlugin

fun <T : ViewBinding> AppCompatActivity.viewBinding(block: () -> T): Pair<ActivityPlugin, Lazy<T>> {
    val binding: Lazy<T> = lazy { block.invoke() }
    return object : ActivityPlugin {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(binding.value.root)
        }
    } to binding
}

val <T : ViewBinding> Pair<ActivityPlugin, Lazy<T>>.plugin: ActivityPlugin
    get() = first

val <T : ViewBinding> Pair<ActivityPlugin, Lazy<T>>.binding: T
    get() = second.value

operator fun <T : ViewBinding> Pair<ActivityPlugin, Lazy<T>>.invoke(): T {
    return second.value
}