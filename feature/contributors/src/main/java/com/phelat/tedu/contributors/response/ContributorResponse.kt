package com.phelat.tedu.contributors.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContributorResponse(
    val contributionNumber: Long,
    val contributionLink: String?,
    val contribution: String,
    val contributor: String,
    val donation: String?,
    val contributorLink: String?,
    val contributorLinkType: String?
)