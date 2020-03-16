package com.phelat.tedu.androidresource.di.module

import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.di.scope.AndroidResourceScope
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.provider.StringIdToStringResource
import com.phelat.tedu.androidresource.resource.StringResource
import dagger.Binds
import dagger.Module

@Module
interface AndroidResourceModule {

    @Binds
    @AndroidResourceScope
    fun bindStringIdToStringResource(
        input: StringIdToStringResource
    ): ResourceProvider<StringId, StringResource>
}