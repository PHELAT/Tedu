package com.phelat.tedu.contributors.datasource

import com.phelat.tedu.contributors.api.ContributorsAPI
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.request.ContributionPageRequest
import com.phelat.tedu.contributors.response.ContributionPageResponse
import com.phelat.tedu.datasource.Readable
import javax.inject.Inject

@ContributorsScope
class ContributionsDataSource @Inject constructor(
    private val api: ContributorsAPI
) : Readable.Suspendable.IO<ContributionPageRequest, ContributionPageResponse> {

    override suspend fun read(input: ContributionPageRequest): ContributionPageResponse {
        return api.getContributionPage(input.pageUrl)
    }
}