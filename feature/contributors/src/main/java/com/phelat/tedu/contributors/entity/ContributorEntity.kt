package com.phelat.tedu.contributors.entity

data class ContributorEntity(
    val contributionNumber: String,
    val contributionLink: String?,
    val contribution: String,
    val contributor: String,
    val contributorLink: String?,
    val contributorLinkType: String?
)