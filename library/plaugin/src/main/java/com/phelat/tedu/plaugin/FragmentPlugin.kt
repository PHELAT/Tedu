package com.phelat.tedu.plaugin

import android.content.Context
import android.os.Bundle

interface FragmentPlugin {

    fun onAttach(context: Context) {}

    fun onCreate(savedInstanceState: Bundle?) {}
}