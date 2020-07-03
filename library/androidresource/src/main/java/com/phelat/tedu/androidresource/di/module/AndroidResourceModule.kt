package com.phelat.tedu.androidresource.di.module

import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringArg
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.provider.StringArgToStringResource
import com.phelat.tedu.androidresource.provider.StringIdToStringResource
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import dagger.Binds
import dagger.Module

@Module
interface AndroidResourceModule {

    @Binds
    @LibraryScope
    fun bindStringIdToStringResource(
        input: StringIdToStringResource
    ): ResourceProvider<StringId, StringResource>

    @Binds
    @LibraryScope
    fun bindStringArgToStringResource(
        input: StringArgToStringResource
    ): ResourceProvider<StringArg, StringResource>
}