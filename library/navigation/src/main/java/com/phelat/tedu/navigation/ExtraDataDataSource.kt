package com.phelat.tedu.navigation

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

object ExtraDataDataSource {

    const val INTER_MODULE_NAVIGATION_EXTRA_DATA = "extraData"

    private const val INTER_MODULE_NAVIGATION_PATH = "imn"

    private lateinit var context: Context

    fun setup(context: Context) {
        this.context = context
        val navigationDirectory = File(context.cacheDir, INTER_MODULE_NAVIGATION_PATH)
        if (navigationDirectory.exists().not()) {
            navigationDirectory.mkdirs()
        }
    }

    internal fun storeExtraData(sign: String, data: Serializable) {
        val outputFile = File(context.cacheDir, getChildPath(sign))
        FileOutputStream(outputFile).use { fileOutputStream ->
            ObjectOutputStream(fileOutputStream).use { stream ->
                stream.writeObject(data)
            }
        }
    }

    internal fun storeExtraData(sign: String, data: Parcelable) {
        val outputFile = File(context.cacheDir, getChildPath(sign))
        FileOutputStream(outputFile).use { fileOutputStream ->
            ObjectOutputStream(fileOutputStream).use { stream ->
                val marshalledData = marshallParcelable(data)
                stream.write(marshalledData)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Serializable> retrieveExtraData(sign: String): T? {
        val outputFile = File(context.cacheDir, getChildPath(sign))
        FileInputStream(outputFile).use { fileInputStream ->
            ObjectInputStream(fileInputStream).use { objectInputStream ->
                return objectInputStream.readObject() as T
            }
        }
    }

    fun <T : Parcelable> retrieveExtraData(sign: String, creator: Parcelable.Creator<T>): T? {
        val outputFile = File(context.cacheDir, getChildPath(sign))
        FileInputStream(outputFile).use { fileInputStream ->
            ObjectInputStream(fileInputStream).use { objectInputStream ->
                val marshalledData = objectInputStream.readBytes()
                return unMarshallParcelable(marshalledData, creator)
            }
        }
    }

    private fun getChildPath(sign: String) = "$INTER_MODULE_NAVIGATION_PATH/$sign"

    private fun marshallParcelable(parcelable: Parcelable): ByteArray {
        val parcel = Parcel.obtain()
        parcelable.writeToParcel(parcel, 0)
        val bytes = parcel.marshall()
        parcel.recycle()
        return bytes
    }

    private fun unMarshall(bytes: ByteArray): Parcel {
        val parcel = Parcel.obtain()
        parcel.unmarshall(bytes, 0, bytes.size)
        parcel.setDataPosition(0)
        return parcel
    }

    private fun <T> unMarshallParcelable(bytes: ByteArray, creator: Parcelable.Creator<T>): T {
        val parcel = unMarshall(bytes)
        val result = creator.createFromParcel(parcel)
        parcel.recycle()
        return result
    }
}