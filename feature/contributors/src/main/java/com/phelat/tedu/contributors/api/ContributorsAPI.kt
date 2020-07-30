package com.phelat.tedu.contributors.api

import com.phelat.tedu.contributors.response.ContributionsResponse
import retrofit2.http.GET

interface ContributorsAPI {

    @GET("PHELAT/Tedu/master/contributions.json")
    suspend fun getContributorsEntryPoint(): ContributionsResponse
}