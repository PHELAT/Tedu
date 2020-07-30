package com.phelat.tedu.contributors.response

import com.phelat.tedu.contributors.entity.ContributorEntity

data class ContributionPageResponse(val contributions: List<ContributorEntity>)