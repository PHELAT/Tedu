package com.phelat.tedu.contributors.repository

import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.request.ContributionPageRequest
import com.phelat.tedu.contributors.response.ContributionPageResponse
import com.phelat.tedu.contributors.response.ContributionsResponse
import com.phelat.tedu.contributors.response.ContributorResponse
import com.phelat.tedu.datasource.Readable
import javax.inject.Inject

@ContributorsScope
class ContributionsRepository @Inject constructor(
    private val entryDataSource: Readable.Suspendable<ContributionsResponse>,
    private val contributionsDataSource: Readable.Suspendable.IO<ContributionPageRequest, ContributionPageResponse>
) : Readable.Suspendable<@JvmSuppressWildcards List<ContributorResponse>> {

    private var contributionsEntry: ContributionsResponse? = null

    private var page = 0

    override suspend fun read(): List<ContributorResponse> {
        if (contributionsEntry == null) {
            contributionsEntry = entryDataSource.read()
        }
        val pageUrl = contributionsEntry?.contributions?.get(page) ?: return emptyList()
        val request = ContributionPageRequest(pageUrl)
        val response = contributionsDataSource.read(request)
        return response.contributions.also { page++ }
    }
}