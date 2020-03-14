package com.phelat.tedu.androidresource.provider

import android.content.Context
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource

class StringIdToStringResource(
    private val context: Context
) : ResourceProvider<StringId, StringResource> {

    override fun getResource(input: StringId): StringResource {
        return StringResource(context.getString(input.id))
    }
}