package com.phelat.tedu.contributors.viewmodel

import androidx.core.text.parseAsHtml
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
import com.phelat.tedu.contributors.entity.ContributorSheetEntity
import com.phelat.tedu.contributors.repository.ContributionsReadable
import com.phelat.tedu.contributors.request.ContributionsRequest
import com.phelat.tedu.contributors.response.ContributorResponse
import com.phelat.tedu.contributors.state.ContributionsViewState
import com.phelat.tedu.contributors.view.ContributorItem
import com.phelat.tedu.contributors.view.PaginationLoadingItem
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.lifecycle.SingleLiveData
import com.phelat.tedu.lifecycle.update
import com.xwray.groupie.Section
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@ContributorsScope
class ContributorsViewModel @Inject constructor(
    private val dataSource: ContributionsReadable,
    private val dispatcher: Dispatcher,
    @Development private val logger: ExceptionLogger,
    private val stringProvider: ResourceProvider<StringId, StringResource>,
    private val stringArgProvider: ResourceProvider<StringArg, StringResource>
) : ViewModel() {

    private val contributorsSection = Section()

    private val _contributorsObservable = MutableLiveData<List<Section>>(
        listOf(contributorsSection)
    )
    val contributorsObservable: LiveData<List<Section>> = _contributorsObservable

    private val _viewStateObservable = MutableLiveData(ContributionsViewState())
    val viewStateObservable: LiveData<ContributionsViewState> = _viewStateObservable

    private val _contributorDetailSheetObservable = SingleLiveData<ContributorSheetEntity>()
    val contributorDetailSheetObservable: LiveData<ContributorSheetEntity> =
        _contributorDetailSheetObservable

    private val _openBrowserObservable = SingleLiveData<String>()
    val openBrowserObservable: LiveData<String> = _openBrowserObservable

    private val _snackBarObservable = SingleLiveData<String>()
    val snackBarObservable: LiveData<String> = _snackBarObservable

    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        isPaginationLoading = false
        viewModelScope.launch {
            delay(DELAY_BEFORE_SHOWING_ERROR_IN_MILLIS)
            logger.log(error)
            _viewStateObservable.update {
                copy(isProgressVisible = false, isErrorVisible = true, isListVisible = false)
            }
        }
    }

    private var isPaginationLoading = false

    private val paginationLoadingItem = PaginationLoadingItem()

    private var page = 0

    private var clickedContributor: ContributorEntity? = null

    init {
        viewModelScope.launch(context = exceptionHandler) {
            _viewStateObservable.update { copy(isProgressVisible = true) }
            fetchContributions().apply(contributorsSection::addAll)
            _viewStateObservable.update { copy(isProgressVisible = false, isListVisible = true) }
            _contributorsObservable.value = listOf(contributorsSection)
        }
    }

    fun onRetryButtonClick() {
        viewModelScope.launch(context = exceptionHandler) {
            delay(DELAY_FOR_RETRY_IN_MILLIS)
            _viewStateObservable.update { copy(isErrorVisible = false, isProgressVisible = true) }
            fetchContributions().apply(contributorsSection::addAll)
            _viewStateObservable.update { copy(isProgressVisible = false, isListVisible = true) }
            _contributorsObservable.value = listOf(contributorsSection)
        }
    }

    private suspend fun fetchContributions(): List<ContributorItem> {
        return withContext(context = dispatcher.iO) {
            dataSource.read(ContributionsRequest(page))
                .run(::mapResponseToEntity)
                .map { entity -> ContributorItem(entity, ::onContributorItemClick) }
                .also { page++ }
        }
    }

    private fun mapResponseToEntity(response: List<ContributorResponse>): List<ContributorEntity> {
        val contributors = mutableListOf<ContributorEntity>()
        val bugReportText = stringProvider.getResource(
            StringId(R.string.contributors_bug_report_text)
        ).resource
        val proposedFeatureText = stringProvider.getResource(
            StringId(R.string.contributors_proposed_feature_text)
        ).resource
        val translationText = stringProvider.getResource(
            StringId(R.string.contributors_translation_text)
        ).resource
        loop@ for (value in response) {
            contributors += ContributorEntity(
                contribution = when (value.contribution) {
                    CONTRIBUTION_BUG_REPORT -> bugReportText
                    CONTRIBUTION_PROPOSED_FEATURE -> proposedFeatureText
                    CONTRIBUTION_DONATION -> {
                        val stringArgs = StringArg(
                            R.string.contributors_donated_text,
                            value.donation ?: continue@loop
                        )
                        stringArgProvider.getResource(stringArgs).resource
                    }
                    CONTRIBUTION_TRANSLATION -> translationText
                    else -> continue@loop
                },
                contributionLink = value.contributionLink,
                contributor = value.contributor,
                contributionNumber = stringArgProvider.getResource(
                    StringArg(
                        R.string.general_number_placeholder,
                        value.contributionNumber
                    )
                ).resource,
                contributorLink = value.contributorLink,
                contributorLinkType = value.contributorLinkType
            )
        }
        return contributors
    }

    private fun onContributorItemClick(entity: ContributorEntity) {
        clickedContributor = entity
        val message = StringArg(
            R.string.contributors_thank_message,
            entity.contributor,
            entity.contribution.toLowerCase(Locale.ENGLISH)
        ).let { stringArg ->
            stringArgProvider.getResource(stringArg).resource
        }
        val contributorLinkType = when (entity.contributorLinkType) {
            CONTRIBUTOR_LINK_TWITTER -> {
                val stringId = StringId(R.string.contributors_contributor_link_twitter_text)
                stringProvider.getResource(stringId).resource
            }
            CONTRIBUTOR_LINK_INSTAGRAM -> {
                val stringId = StringId(R.string.contributors_contributor_link_instagram_text)
                stringProvider.getResource(stringId).resource
            }
            CONTRIBUTOR_LINK_GITHUB -> {
                val stringId = StringId(R.string.contributors_contributor_link_github_text)
                stringProvider.getResource(stringId).resource
            }
            else -> null
        }
        val contributorLinkText = if (contributorLinkType != null) {
            val stringArgs = StringArg(
                R.string.contributors_contributor_link_text,
                entity.contributor,
                contributorLinkType
            )
            stringArgProvider.getResource(stringArgs).resource
        } else {
            null
        }
        _contributorDetailSheetObservable.value = ContributorSheetEntity(
            sheetTitle = message.parseAsHtml(),
            isLinkToContributionVisible = entity.contributionLink.isNullOrEmpty().not(),
            isLinkToContributorVisible = entity.contributorLink.isNullOrEmpty()
                .not()
                .and(contributorLinkType != null),
            linkToContributorText = contributorLinkText ?: ""
        )
    }

    fun onReachTheEndOfList() {
        if (isPaginationLoading) return
        isPaginationLoading = true
        contributorsSection.setFooter(paginationLoadingItem)
        viewModelScope.launch(context = exceptionHandler) {
            fetchContributions().apply(contributorsSection::addAll)
            contributorsSection.removeFooter()
            _contributorsObservable.value = listOf(contributorsSection)
            isPaginationLoading = false
        }
    }

    fun onContributionLinkClick() {
        clickedContributor?.also { contributorEntity ->
            viewModelScope.launch {
                delay(DELAY_BEFORE_OPENING_LINK_IN_MILLIS)
                _openBrowserObservable.value = contributorEntity.contributionLink
            }
        }
    }

    fun onActivityNotFoundForBrowser() {
        val messageId = StringId(R.string.contributors_no_browser_found_message)
        val message = stringProvider.getResource(messageId).resource
        _snackBarObservable.value = message
    }

    fun onContributorLinkClick() {
        clickedContributor?.also { contributorEntity ->
            viewModelScope.launch {
                delay(DELAY_BEFORE_OPENING_LINK_IN_MILLIS)
                _openBrowserObservable.value = requireNotNull(contributorEntity.contributorLink)
            }
        }
    }

    companion object {
        private const val DELAY_FOR_RETRY_IN_MILLIS = 200L
        private const val DELAY_BEFORE_SHOWING_ERROR_IN_MILLIS = 500L
        private const val DELAY_BEFORE_OPENING_LINK_IN_MILLIS = 200L
        private const val CONTRIBUTION_BUG_REPORT = "bug-report"
        private const val CONTRIBUTION_PROPOSED_FEATURE = "proposed-feature"
        private const val CONTRIBUTION_DONATION = "donated"
        private const val CONTRIBUTION_TRANSLATION = "translation"
        private const val CONTRIBUTOR_LINK_TWITTER = "twitter"
        private const val CONTRIBUTOR_LINK_INSTAGRAM = "instagram"
        private const val CONTRIBUTOR_LINK_GITHUB = "github"
    }
}