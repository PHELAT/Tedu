package com.phelat.tedu.todolist.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.phelat.tedu.daggerandroid.Injector
import com.phelat.tedu.todo.viewmodel.TodoViewModel
import com.phelat.tedu.todolist.R
import com.phelat.tedu.todolist.di.component.TodoListComponent
import kotlinx.android.synthetic.main.fragment_todolist.addTodoButton
import kotlinx.android.synthetic.main.fragment_todolist.todoListRecycler
import javax.inject.Inject

class TodoListFragment : Fragment(R.layout.fragment_todolist) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val todoViewModel by activityViewModels<TodoViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        Injector.inject(TodoListComponent::class, this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todoAdapter = TodoListAdapter()
        with(todoListRecycler) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
        }
        todoViewModel.todoObservable.observe(viewLifecycleOwner, todoAdapter::update)
        addTodoButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_addtodo)
        }
    }
}