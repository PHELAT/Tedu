package com.phelat.tedu.contributors.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.phelat.tedu.androiddagger.inject
import com.phelat.tedu.contributors.R
import com.phelat.tedu.contributors.di.component.ContributorsComponent
import com.phelat.tedu.contributors.entity.ContributorSheetEntity
import com.phelat.tedu.contributors.state.ContributionsViewState
import com.phelat.tedu.contributors.viewmodel.ContributorsViewModel
import com.phelat.tedu.designsystem.ext.makeLongSnackBar
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.phelat.tedu.sdkextensions.onReachTheEnd
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_contributors.contributionProgress
import kotlinx.android.synthetic.main.fragment_contributors.contributorsRecycler
import kotlinx.android.synthetic.main.fragment_contributors.errorGroup
import kotlinx.android.synthetic.main.fragment_contributors.retryButton
import javax.inject.Inject

class ContributorsFragment : Fragment(R.layout.fragment_contributors) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ContributorsViewModel by viewModels { viewModelFactory }

    private var contributorSheet: ContributorSheet? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject<ContributorsComponent>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = GroupAdapter<GroupieViewHolder>()
        contributorsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
            onReachTheEnd { viewModel.onReachTheEndOfList() }
        }
        retryButton.setOnClickListener {
            viewModel.onRetryButtonClick()
        }
        viewModel.apply {
            contributorsObservable.observe(viewLifecycleOwner, adapter::update)
            viewStateObservable.observe(viewLifecycleOwner, ::observeViewState)
            contributorDetailSheetObservable.observe(
                viewLifecycleOwner,
                ::observeContributorDetailSheetObservable
            )
            openBrowserObservable.observe(viewLifecycleOwner, ::observeOpenBrowser)
            snackBarObservable.observe(viewLifecycleOwner, ::observeSnackBar)
        }
    }

    private fun observeViewState(state: ContributionsViewState) {
        contributionProgress.isVisible = state.isProgressVisible
        errorGroup.isVisible = state.isErrorVisible
        contributorsRecycler.isVisible = state.isListVisible
    }

    private fun observeContributorDetailSheetObservable(entity: ContributorSheetEntity) {
        val sheetSettings: (ContributorSheet) -> Unit = { sheet ->
            sheet.sheetTitle = entity.sheetTitle
            sheet.onContributionLinkClick = viewModel::onContributionLinkClick
            sheet.isLinkToContributionVisible = entity.isLinkToContributionVisible
            sheet.isContributorLinkVisible = entity.isLinkToContributorVisible
            sheet.contributorLinkText = entity.linkToContributorText
            sheet.onContributorLinkClick = viewModel::onContributorLinkClick
        }
        contributorSheet?.also(sheetSettings::invoke)?.show() ?: run {
            contributorSheet = ContributorSheet(requireContext())
            observeContributorDetailSheetObservable(entity)
        }
    }

    private fun observeOpenBrowser(url: String) {
        try {
            Intent(Intent.ACTION_VIEW).apply { data = url.toUri() }
                .also(::startActivity)
        } catch (ignored: ActivityNotFoundException) {
            viewModel.onActivityNotFoundForBrowser()
        }
    }

    private fun observeSnackBar(message: String) {
        requireActivity().makeLongSnackBar(message).show()
    }
}