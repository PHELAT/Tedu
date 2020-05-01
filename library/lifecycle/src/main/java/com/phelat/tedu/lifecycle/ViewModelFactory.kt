package com.phelat.tedu.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val viewModelProviders: ViewModelProviders
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val provider = requireNotNull(viewModelProviders[modelClass]) {
            "${modelClass.name} isn't registered in your dagger graph!"
        }
        return provider.get() as T
    }
}