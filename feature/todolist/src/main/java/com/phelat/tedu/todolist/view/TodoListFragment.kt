package com.phelat.tedu.todolist.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.phelat.tedu.daggerandroid.Injector
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todolist.R
import com.phelat.tedu.todolist.di.component.TodoListComponent
import kotlinx.android.synthetic.main.fragment_todolist.addTodoButton
import kotlinx.android.synthetic.main.fragment_todolist.todoListRecycler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoListFragment : Fragment() {

    @Inject
    lateinit var todoReadableDataSource: Readable<Flow<List<TodoEntity>>>

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
            lifecycleScope.launch {
                todoReadableDataSource.read()
                    .flowOn(Dispatchers.IO)
                    .collect {
                        withContext(Dispatchers.Main) {
                            adapter = TodoListAdapter(it)
                        }
                    }
            }
        }
        addTodoButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_addtodo)
        }
    }

}