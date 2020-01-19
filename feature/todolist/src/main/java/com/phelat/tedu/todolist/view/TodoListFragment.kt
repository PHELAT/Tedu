package com.phelat.tedu.todolist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.phelat.tedu.todolist.R
import kotlinx.android.synthetic.main.fragment_todolist.todoListRecycler

class TodoListFragment : Fragment() {

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
    }

}