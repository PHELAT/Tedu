package com.phelat.tedu.contributors.di.module

import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.view.ContributorsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ContributorsFragmentModule {

    @ContributorsScope
    @ContributesAndroidInjector(modules = [ContributorsViewModelModule::class])
    fun bindContributorsFragment(): ContributorsFragment
}