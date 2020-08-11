package com.phelat.tedu.contributors.di.module

import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.view.ContributorsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface ContributorsFragmentModule {

    @ContributorsScope
    @ContributesAndroidInjector(
        modules = [
            ContributorsViewModelModule::class,
            ContributorsNetworkModule::class,
            ContributionsDataModule::class
        ]
    )
    fun bindContributorsFragment(): ContributorsFragment
}