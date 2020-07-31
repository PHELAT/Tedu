package com.phelat.tedu.contributors.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContributionPageRequest(val pageUrl: String)