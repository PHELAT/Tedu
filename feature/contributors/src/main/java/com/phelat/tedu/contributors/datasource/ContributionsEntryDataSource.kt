package com.phelat.tedu.contributors.datasource

import com.phelat.tedu.contributors.api.ContributorsAPI
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.response.ContributionsResponse
import com.phelat.tedu.datasource.Readable
import javax.inject.Inject

@ContributorsScope
internal class ContributionsEntryDataSource @Inject constructor(
    private val api: ContributorsAPI
) : ContributionsEntryReadable {

    override suspend fun read(): ContributionsResponse {
        return api.getContributorsEntryPoint()
    }
}

typealias ContributionsEntryReadable = Readable.Suspendable<ContributionsResponse>