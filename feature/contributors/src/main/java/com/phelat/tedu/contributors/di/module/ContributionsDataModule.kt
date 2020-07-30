package com.phelat.tedu.contributors.di.module

import com.phelat.tedu.contributors.datasource.ContributionsEntryDataSource
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.response.ContributionsResponse
import com.phelat.tedu.datasource.Readable
import dagger.Binds
import dagger.Module

@Module
interface ContributionsDataModule {

    @Binds
    @ContributorsScope
    fun bindContributionsDataSource(
        input: ContributionsEntryDataSource
    ): Readable.Suspendable<ContributionsResponse>
}