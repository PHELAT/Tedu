package com.phelat.tedu.contributors.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.phelat.tedu.androiddagger.inject
import com.phelat.tedu.contributors.R
import com.phelat.tedu.contributors.di.component.ContributorsComponent
import com.phelat.tedu.contributors.viewmodel.ContributorsViewModel
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_contributors.contributorsRecycler
import javax.inject.Inject

class ContributorsFragment : Fragment(R.layout.fragment_contributors) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ContributorsViewModel by viewModels { viewModelFactory }

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
        }
        viewModel.apply {
            contributorsObservable.observe(viewLifecycleOwner, adapter::update)
        }
    }
}