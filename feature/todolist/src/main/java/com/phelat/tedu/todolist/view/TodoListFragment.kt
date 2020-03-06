package com.phelat.tedu.todolist.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.phelat.tedu.daggerandroid.Injector
import com.phelat.tedu.todolist.R
import com.phelat.tedu.todolist.di.component.TodoListComponent
import com.phelat.tedu.todolist.viewmodel.TodoListViewModel
import kotlinx.android.synthetic.main.fragment_todolist.addTodoButton
import kotlinx.android.synthetic.main.fragment_todolist.todoListRecycler
import javax.inject.Inject

class TodoListFragment : Fragment(R.layout.fragment_todolist) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val todoListViewModel by viewModels<TodoListViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        Injector.inject(TodoListComponent::class, this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTodoButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_addtodo)
        }
        val todoAdapter = TodoListAdapter(onClickListener = todoListViewModel::onTodoClick)
        with(todoListRecycler) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
        }
        todoListViewModel.todoObservable.observe(viewLifecycleOwner, todoAdapter::update)
    }
}