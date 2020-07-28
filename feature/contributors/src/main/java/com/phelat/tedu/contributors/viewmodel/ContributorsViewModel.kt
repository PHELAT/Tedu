package com.phelat.tedu.contributors.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.entity.ContributorEntity
import com.phelat.tedu.contributors.view.ContributorItem
import javax.inject.Inject

@ContributorsScope
class ContributorsViewModel @Inject constructor() : ViewModel() {

    private val _contributorsObservable = MutableLiveData<List<ContributorItem>>()
    val contributorsObservable: LiveData<List<ContributorItem>> = _contributorsObservable

    init {
        _contributorsObservable.value = listOf(
            ContributorItem(
                ContributorEntity(
                    contributionNumber = 2,
                    contribution = "Reported an issue"
                )
            ),
            ContributorItem(
                ContributorEntity(
                    contributionNumber = 1,
                    contribution = "Proposed a feature"
                )
            )
        )
    }
}