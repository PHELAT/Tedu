package com.phelat.tedu.navigation

import android.os.Parcelable
import java.io.Serializable

interface ExtraDataBag {

    fun storeData(key: String, data: Parcelable)
    fun storeData(key: String, data: Serializable)

    fun <T : Parcelable> getParcel(key: String): T?
    fun <T : Serializable> getSerial(key: String): T?
}
