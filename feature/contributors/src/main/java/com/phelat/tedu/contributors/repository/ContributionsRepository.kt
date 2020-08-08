package com.phelat.tedu.contributors.repository

import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.request.ContributionPageRequest
import com.phelat.tedu.contributors.request.ContributionsRequest
import com.phelat.tedu.contributors.response.ContributionPageResponse
import com.phelat.tedu.contributors.response.ContributionsResponse
import com.phelat.tedu.contributors.response.ContributorResponse
import com.phelat.tedu.datasource.Readable
import javax.inject.Inject

@ContributorsScope
class ContributionsRepository @Inject constructor(
    private val entryDataSource: Readable.Suspendable<ContributionsResponse>,
    private val contributionsDataSource: Readable.Suspendable.IO<ContributionPageRequest,
            ContributionPageResponse>
) : Readable.Suspendable.IO<ContributionsRequest, @JvmSuppressWildcards List<ContributorResponse>> {

    private var contributionsEntry: ContributionsResponse? = null

    override suspend fun read(input: ContributionsRequest): List<ContributorResponse> {
        if (contributionsEntry == null) {
            contributionsEntry = entryDataSource.read()
        }
        if (input.page > (contributionsEntry?.contributions?.size ?: -1) - 1) {
            return emptyList()
        }
        return contributionsEntry?.contributions?.get(input.page)
            ?.run(::ContributionPageRequest)
            ?.let { request -> contributionsDataSource.read(request).contributions }
            ?: emptyList()
    }
}