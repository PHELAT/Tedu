package com.phelat.tedu.contributors.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.analytics.ExceptionLogger
import com.phelat.tedu.analytics.di.qualifier.Development
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.entity.ContributorEntity
import com.phelat.tedu.contributors.response.ContributionsResponse
import com.phelat.tedu.contributors.view.ContributorItem
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Readable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@ContributorsScope
class ContributorsViewModel @Inject constructor(
    private val datasource: Readable.Suspendable<ContributionsResponse>,
    dispatcher: Dispatcher,
    @Development private val logger: ExceptionLogger
) : ViewModel() {

    private val _contributorsObservable = MutableLiveData<List<ContributorItem>>()
    val contributorsObservable: LiveData<List<ContributorItem>> = _contributorsObservable

    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        // TODO: handle error
        logger.log(error)
    }

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
        viewModelScope.launch(context = dispatcher.iO + exceptionHandler) {
            println(datasource.read())
        }
    }
}