package com.phelat.tedu.contributors.datasource

import com.phelat.tedu.contributors.api.ContributorsAPI
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.request.ContributionPageRequest
import com.phelat.tedu.contributors.response.ContributionPageResponse
import com.phelat.tedu.datasource.Readable
import javax.inject.Inject

@ContributorsScope
internal class ContributionsDataSource @Inject constructor(
    private val api: ContributorsAPI
) : ContributionPageReadable {

    override suspend fun read(input: ContributionPageRequest): ContributionPageResponse {
        return api.getContributionPage(input.pageUrl)
    }
}

typealias ContributionPageReadable = Readable.Suspendable.IO<ContributionPageRequest,
        ContributionPageResponse>