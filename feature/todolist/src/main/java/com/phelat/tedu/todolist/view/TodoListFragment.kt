package com.phelat.tedu.todolist.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.phelat.tedu.androiddagger.inject
import com.phelat.tedu.designsystem.component.view.BottomSheetView
import com.phelat.tedu.designsystem.entity.BottomSheetEntity
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.phelat.tedu.navigation.observeNavigation
import com.phelat.tedu.plaugin.FragmentPlugin
import com.phelat.tedu.plaugin.PlauginFragment
import com.phelat.tedu.plugins.invoke
import com.phelat.tedu.plugins.plugin
import com.phelat.tedu.plugins.viewBinding
import com.phelat.tedu.todolist.R
import com.phelat.tedu.todolist.databinding.FragmentTodolistBinding
import com.phelat.tedu.todolist.di.component.TodoListComponent
import com.phelat.tedu.todolist.viewmodel.TodoListViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.viewbinding.GroupieViewHolder
import javax.inject.Inject

class TodoListFragment : PlauginFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val todoListViewModel: TodoListViewModel by viewModels { viewModelFactory }

    private var todoSheet: BottomSheetView? = null

    private val viewBinding = viewBinding { inflater, viewGroup ->
        FragmentTodolistBinding.inflate(inflater, viewGroup, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject<TodoListComponent>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val groupAdapter = GroupAdapter<GroupieViewHolder<*>>()
        viewBinding().todoListRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = groupAdapter
        }
        todoListViewModel.apply {
            todoObservable.observe(viewLifecycleOwner, groupAdapter::update)
            todoSheetObservable.observe(viewLifecycleOwner, ::observeTodoSheet)
            dismissTodoSheetObservable.observe(viewLifecycleOwner) { dismissTodoSheet() }
            todoDeletionObservable.observe(viewLifecycleOwner) { observeTodoDeletion() }
            navigationObservable.observeNavigation(this@TodoListFragment)
            snackBarObservable.observe(viewLifecycleOwner, ::showSnackBar)
        }
    }

    private fun observeTodoDeletion() {
        Snackbar.make(
            viewBinding().viewRoot,
            R.string.todolist_todo_deletion_message,
            Snackbar.LENGTH_LONG
        ).setAction(R.string.todolist_undo_deletion_action) {
            todoListViewModel.onTodoDeletionUndoClick()
        }.show()
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
        Snackbar.make(viewBinding().viewRoot, message, Snackbar.LENGTH_LONG).show()
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

    override fun plugins(): MutableList<FragmentPlugin> = mutableListOf(viewBinding.plugin)
}