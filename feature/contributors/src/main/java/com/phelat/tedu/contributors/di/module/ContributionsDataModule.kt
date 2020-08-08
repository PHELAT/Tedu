package com.phelat.tedu.contributors.di.module

import com.phelat.tedu.contributors.datasource.ContributionPageReadable
import com.phelat.tedu.contributors.datasource.ContributionsDataSource
import com.phelat.tedu.contributors.datasource.ContributionsEntryDataSource
import com.phelat.tedu.contributors.datasource.ContributionsEntryReadable
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.repository.ContributionsReadable
import com.phelat.tedu.contributors.repository.ContributionsRepository
import dagger.Binds
import dagger.Module

@Module
internal interface ContributionsDataModule {

    @Binds
    @ContributorsScope
    fun bindContributionsEntryDataSource(
        input: ContributionsEntryDataSource
    ): ContributionsEntryReadable

    @Binds
    @ContributorsScope
    fun bindContributionsDataSource(input: ContributionsDataSource): ContributionPageReadable

    @Binds
    @ContributorsScope
    fun bindContributionRepository(input: ContributionsRepository): ContributionsReadable
}