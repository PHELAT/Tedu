package com.phelat.tedu.androidresource.provider

import android.content.Context
import com.phelat.tedu.androidcore.di.qualifier.ApplicationContext
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.di.scope.AndroidResourceScope
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import javax.inject.Inject

@AndroidResourceScope
class StringIdToStringResource @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceProvider<StringId, StringResource> {

    override fun getResource(input: StringId): StringResource {
        return StringResource(context.getString(input.id))
    }
}