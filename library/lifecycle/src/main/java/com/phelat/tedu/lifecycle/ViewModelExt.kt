package com.phelat.tedu.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> viewModelFactory(crossinline factory: () -> T): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }
}