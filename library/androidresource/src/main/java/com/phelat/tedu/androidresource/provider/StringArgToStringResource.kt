package com.phelat.tedu.androidresource.provider

import android.content.Context
import com.phelat.tedu.androidcore.di.qualifier.ApplicationContext
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringArg
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import javax.inject.Inject

@LibraryScope
class StringArgToStringResource @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceProvider<StringArg, StringResource> {

    override fun getResource(input: StringArg): StringResource {
        return StringResource(context.getString(input.stringId, *input.stringArgs))
    }
}