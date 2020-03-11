package com.phelat.tedu.todolist.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.phelat.tedu.designsystem.component.view.BottomSheetView
import com.phelat.tedu.designsystem.entity.BottomSheetItemEntity
import com.phelat.tedu.todolist.R
import com.phelat.tedu.todolist.viewmodel.TodoListViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_todolist.todoListRecycler
import kotlinx.android.synthetic.main.fragment_todolist.viewRoot
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodoListFragment : Fragment(R.layout.fragment_todolist) {

    private val todoListViewModel by viewModel<TodoListViewModel>()

    private var todoSheet: BottomSheetView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        todoListRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = groupAdapter
        }
        todoListViewModel.apply {
            todoObservable.observe(viewLifecycleOwner, groupAdapter::update)
            todoSheetObservable.observe(viewLifecycleOwner, ::observeTodoSheet)
            dismissTodoSheetObservable.observe(viewLifecycleOwner) { dismissTodoSheet() }
            todoDeletionObservable.observe(viewLifecycleOwner) { observeTodoDeletion() }
            navigationObservable.observe(viewLifecycleOwner) { navigationPair ->
                findNavController().navigate(navigationPair.first, navigationPair.second)
            }
        }
    }

    private fun observeTodoDeletion() {
        Snackbar.make(viewRoot, R.string.todolist_todo_deletion_message, Snackbar.LENGTH_LONG)
            .setAction(R.string.general_undo_text) {
                todoListViewModel.onTodoDeletionUndoClick()
            }
            .show()
    }

    private fun observeTodoSheet(items: List<BottomSheetItemEntity>) {
        todoSheet?.setItems(items)
            ?.show()
            ?: run {
                todoSheet = BottomSheetView(requireContext())
                observeTodoSheet(items)
            }
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