package com.phelat.tedu.contributors.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.analytics.ExceptionLogger
import com.phelat.tedu.analytics.di.qualifier.Development
import com.phelat.tedu.androidresource.ResourceProvider
import com.phelat.tedu.androidresource.input.StringArg
import com.phelat.tedu.androidresource.input.StringId
import com.phelat.tedu.androidresource.resource.StringResource
import com.phelat.tedu.contributors.R
import com.phelat.tedu.contributors.di.scope.ContributorsScope
import com.phelat.tedu.contributors.entity.ContributorEntity
import com.phelat.tedu.contributors.response.ContributorResponse
import com.phelat.tedu.contributors.state.ContributionsViewState
import com.phelat.tedu.contributors.view.ContributorItem
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.lifecycle.update
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ContributorsScope
class ContributorsViewModel @Inject constructor(
    private val dataSource: Readable.Suspendable<List<ContributorResponse>>,
    private val dispatcher: Dispatcher,
    @Development private val logger: ExceptionLogger,
    private val stringProvider: ResourceProvider<StringId, StringResource>,
    private val stringArgProvider: ResourceProvider<StringArg, StringResource>
) : ViewModel() {

    private val _contributorsObservable = MutableLiveData<List<ContributorItem>>()
    val contributorsObservable: LiveData<List<ContributorItem>> = _contributorsObservable

    private val _viewStateObservable = MutableLiveData(ContributionsViewState())
    val viewStateObservable: LiveData<ContributionsViewState> = _viewStateObservable

    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        viewModelScope.launch {
            delay(DELAY_BEFORE_SHOWING_ERROR_IN_MILLIS)
            logger.log(error)
            _viewStateObservable.update { copy(isProgressVisible = false, isErrorVisible = true) }
        }
    }

    init {
        viewModelScope.launch(context = exceptionHandler) {
            fetchContributions()
        }
    }

    fun onRetryButtonClick() {
        viewModelScope.launch(context = exceptionHandler) {
            delay(DELAY_FOR_RETRY_IN_MILLIS)
            _viewStateObservable.update { copy(isErrorVisible = false) }
            fetchContributions()
        }
    }

    private suspend fun fetchContributions() {
        _viewStateObservable.update { copy(isProgressVisible = true) }
        withContext(context = dispatcher.iO) {
            dataSource.read()
                .run(::mapResponseToEntity)
                .map(::ContributorItem)
        }.also {
            _viewStateObservable.update { copy(isProgressVisible = false) }
        }.also(_contributorsObservable::setValue)
    }

    private fun mapResponseToEntity(response: List<ContributorResponse>): List<ContributorEntity> {
        val contributors = mutableListOf<ContributorEntity>()
        val bugReportText = stringProvider.getResource(StringId(R.string.contributors_bug_report_text))
        loop@ for (value in response) {
            contributors += ContributorEntity(
                contribution = when (value.contribution) {
                    CONTRIBUTION_BUG_REPORT -> bugReportText.resource
                    else -> continue@loop
                },
                contributionLink = value.contributionLink,
                contributor = value.contributor,
                contributionNumber = stringArgProvider.getResource(
                    StringArg(
                        R.string.general_number_placeholder,
                        value.contributionNumber
                    )
                ).resource
            )
        }
        return contributors
    }

    companion object {
        private const val DELAY_FOR_RETRY_IN_MILLIS = 200L
        private const val DELAY_BEFORE_SHOWING_ERROR_IN_MILLIS = 500L
        private const val CONTRIBUTION_BUG_REPORT = "bug-report"
    }
}