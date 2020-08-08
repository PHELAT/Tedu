package com.phelat.tedu.backup.di.module

import com.phelat.tedu.backup.BackupSyncRepository
import com.phelat.tedu.backup.datasource.WebDavCredentialsDataSource
import com.phelat.tedu.backup.datasource.WebDavCredentialsReadable
import com.phelat.tedu.backup.datasource.WebDavCredentialsWritable
import com.phelat.tedu.backup.datasource.WebDavDataSource
import com.phelat.tedu.backup.datasource.WebDavReadable
import com.phelat.tedu.backup.datasource.WebDavWritable
import com.phelat.tedu.backup.repository.WebDavSyncRepository
import com.phelat.tedu.backup.usecase.BackupUseCase
import com.phelat.tedu.backup.usecase.WebDavBackupUseCase
import com.phelat.tedu.dependencyinjection.feature.FeatureScope
import dagger.Binds
import dagger.Module

@Module
internal interface WebDavBindingsModule {

    @Binds
    @FeatureScope
    fun bindWritableWebDavCredentialsDataSource(
        input: WebDavCredentialsDataSource
    ): WebDavCredentialsWritable

    @Binds
    @FeatureScope
    fun bindReadableWebDavCredentialsDataSource(
        input: WebDavCredentialsDataSource
    ): WebDavCredentialsReadable

    @Binds
    @FeatureScope
    fun bindReadableWebDavDataSource(input: WebDavDataSource): WebDavReadable

    @Binds
    @FeatureScope
    fun bindWritableWebDavDataSource(input: WebDavDataSource): WebDavWritable

    @Binds
    @FeatureScope
    fun bindWebDavRepository(
        input: WebDavSyncRepository
    ): BackupSyncRepository

    @Binds
    @FeatureScope
    fun bindWebDavBackupUseCase(input: WebDavBackupUseCase): BackupUseCase
}