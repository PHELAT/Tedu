package com.phelat.tedu.contributors.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContributorEntity(
    val contributionNumber: Long,
    val contributionLink: String,
    val contribution: String
)