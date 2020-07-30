package com.phelat.tedu.contributors.datasource

import com.phelat.tedu.contributors.api.ContributorsAPI
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.response.ContributionsResponse
import com.phelat.tedu.datasource.Readable
import javax.inject.Inject

@ContributorsScope
class ContributionsDataSource @Inject constructor(
    private val api: ContributorsAPI
) : Readable.Suspendable<ContributionsResponse> {

    override suspend fun read(): ContributionsResponse {
        return api.getContributorsEntryPoint()
    }
}