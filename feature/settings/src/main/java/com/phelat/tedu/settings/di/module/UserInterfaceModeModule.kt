package com.phelat.tedu.settings.di.module

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.dependencyinjection.scope.FeatureScope
import com.phelat.tedu.settings.datasource.UserInterfaceModeDataSource
import com.phelat.tedu.settings.entity.UserInterfaceMode
import dagger.Binds
import dagger.Module

@Module
interface UserInterfaceModeModule {

    @Binds
    @FeatureScope
    fun bindUserInterfaceModeDataSourceToReadable(
        input: UserInterfaceModeDataSource
    ): Readable<UserInterfaceMode>

    @Binds
    @FeatureScope
    fun bindUserInterfaceModeDataSourceToWritable(
        input: UserInterfaceModeDataSource
    ): Writable<UserInterfaceMode>
}