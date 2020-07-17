package com.phelat.tedu.plugins

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.phelat.tedu.navigation.ExtraDataDataSource
import com.phelat.tedu.plaugin.FragmentPlugin
import java.io.Serializable

class SerializableImnPlugin<T : Fragment, D : Serializable>(
    private val fragment: T,
    private val onExtraDataReceived: (data: D) -> Unit
) : FragmentPlugin {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (fragment.arguments == null) return
        val bundle = fragment.requireArguments()
        val sign = bundle.getString(ExtraDataDataSource.INTER_MODULE_NAVIGATION_EXTRA_DATA)
        if (sign.isNullOrEmpty()) return
        ExtraDataDataSource.retrieveExtraData<D>(sign)?.apply(onExtraDataReceived)
    }
}