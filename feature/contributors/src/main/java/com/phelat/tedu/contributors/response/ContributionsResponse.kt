package com.phelat.tedu.contributors.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContributionsResponse(
    val contributions: List<String>
)