package com.phelat.tedu.contributors.repository

import com.phelat.tedu.contributors.datasource.ContributionPageReadable
import com.phelat.tedu.contributors.datasource.ContributionsEntryReadable
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.request.ContributionPageRequest
import com.phelat.tedu.contributors.request.ContributionsRequest
import com.phelat.tedu.contributors.response.ContributionsResponse
import com.phelat.tedu.contributors.response.ContributorResponse
import com.phelat.tedu.datasource.Readable
import javax.inject.Inject

@ContributorsScope
internal class ContributionsRepository @Inject constructor(
    private val entryDataSource: ContributionsEntryReadable,
    private val contributionsDataSource: ContributionPageReadable
) : ContributionsReadable {

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

typealias ContributionsReadable = Readable.Suspendable.IO<ContributionsRequest,
        @JvmSuppressWildcards List<ContributorResponse>>