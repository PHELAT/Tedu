package com.phelat.tedu.contributors.di.module

import com.phelat.tedu.contributors.datasource.ContributionsDataSource
import com.phelat.tedu.contributors.datasource.ContributionsEntryDataSource
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.repository.ContributionsRepository
import com.phelat.tedu.contributors.request.ContributionPageRequest
import com.phelat.tedu.contributors.response.ContributionPageResponse
import com.phelat.tedu.contributors.response.ContributionsResponse
import com.phelat.tedu.contributors.response.ContributorResponse
import com.phelat.tedu.datasource.Readable
import dagger.Binds
import dagger.Module

@Module
interface ContributionsDataModule {

    @Binds
    @ContributorsScope
    fun bindContributionsEntryDataSource(
        input: ContributionsEntryDataSource
    ): Readable.Suspendable<ContributionsResponse>

    @Binds
    @ContributorsScope
    fun bindContributionsDataSource(
        input: ContributionsDataSource
    ): Readable.Suspendable.IO<ContributionPageRequest, ContributionPageResponse>

    @Binds
    @ContributorsScope
    fun bindContributionRepository(
        input: ContributionsRepository
    ): Readable.Suspendable<List<ContributorResponse>>
}