package com.phelat.tedu.futuretodolist.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.phelat.tedu.androiddagger.inject
import com.phelat.tedu.designsystem.component.view.BottomSheetView
import com.phelat.tedu.designsystem.entity.BottomSheetEntity
import com.phelat.tedu.futuretodolist.R
import com.phelat.tedu.futuretodolist.di.component.FutureTodoListComponent
import com.phelat.tedu.futuretodolist.viewmodel.FutureTodoListViewModel
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.phelat.tedu.navigation.observeNavigation
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_future_todolist.futureTodoListRecycler
import kotlinx.android.synthetic.main.fragment_future_todolist.viewRoot
import javax.inject.Inject

class FutureTodoListFragment : Fragment(R.layout.fragment_future_todolist) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val futureTodoListViewModel: FutureTodoListViewModel by viewModels { viewModelFactory }

    private var todoSheet: BottomSheetView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject<FutureTodoListComponent>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        futureTodoListRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = groupAdapter
        }
        futureTodoListViewModel.apply {
            todoObservable.observe(viewLifecycleOwner, groupAdapter::update)
            todoSheetObservable.observe(viewLifecycleOwner, ::observeTodoSheet)
            dismissTodoSheetObservable.observe(viewLifecycleOwner) { dismissTodoSheet() }
            todoDeletionObservable.observe(viewLifecycleOwner) { observeTodoDeletion() }
            navigationObservable.observeNavigation(this@FutureTodoListFragment)
            snackBarObservable.observe(viewLifecycleOwner, ::showSnackBar)
        }
    }

    private fun observeTodoDeletion() {
        Snackbar.make(viewRoot, R.string.todolist_todo_deletion_message, Snackbar.LENGTH_LONG)
            .setAction(R.string.todolist_undo_deletion_action) {
                futureTodoListViewModel.onTodoDeletionUndoClick()
            }
            .show()
    }

    private fun observeTodoSheet(entity: BottomSheetEntity) {
        todoSheet?.apply {
            sheetItems = entity.items
            sheetTitle = entity.sheetTitle
            show()
        } ?: run {
            todoSheet = BottomSheetView(requireContext())
            observeTodoSheet(entity)
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(viewRoot, message, Snackbar.LENGTH_LONG).show()
    }

    private fun dismissTodoSheet() {
        if (todoSheet?.isShowing == true) {
            todoSheet?.dismiss()
            todoSheet = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissTodoSheet()
    }
}