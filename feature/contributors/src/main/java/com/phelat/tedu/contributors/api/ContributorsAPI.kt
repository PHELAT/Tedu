package com.phelat.tedu.contributors.api

import com.phelat.tedu.contributors.response.ContributionPageResponse
import com.phelat.tedu.contributors.response.ContributionsResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface ContributorsAPI {

    @GET("PHELAT/Tedu/master/contributions.json")
    suspend fun getContributorsEntryPoint(): ContributionsResponse

    @GET
    suspend fun getContributionPage(@Url pageUrl: String): ContributionPageResponse
}