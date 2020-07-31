package com.phelat.tedu.contributors.response

import com.phelat.tedu.contributors.entity.ContributorEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContributionPageResponse(val contributions: List<ContributorEntity>)