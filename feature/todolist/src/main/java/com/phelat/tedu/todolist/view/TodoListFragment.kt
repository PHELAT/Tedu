package com.phelat.tedu.todolist.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.phelat.tedu.daggerandroid.Injector
import com.phelat.tedu.todolist.R
import com.phelat.tedu.todolist.di.component.TodoListComponent
import kotlinx.android.synthetic.main.fragment_todolist.addTodoButton
import kotlinx.android.synthetic.main.fragment_todolist.todoListRecycler

class TodoListFragment : Fragment() {

    override fun onAttach(context: Context) {
        Injector.inject(TodoListComponent::class, this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todolist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(todoListRecycler) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TodoListAdapter(mutableListOf("Do this", "And that"))
        }
        addTodoButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_addtodo)
        }
    }

}