package com.phelat.tedu.backup.di.module

import com.phelat.tedu.backup.di.scope.BackupScope
import com.phelat.tedu.backup.view.WebDavSetupFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface BackupFragmentModule {

    @BackupScope
    @ContributesAndroidInjector(modules = [BackupViewModelModule::class])
    fun bindWebDavSetupFragment(): WebDavSetupFragment
}