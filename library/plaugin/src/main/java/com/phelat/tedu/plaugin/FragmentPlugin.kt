package com.phelat.tedu.plaugin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface FragmentPlugin {

    fun onAttach(context: Context) {}

    fun onCreate(savedInstanceState: Bundle?) {}

    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = null

    fun onDestroyView() {}
}