package com.phelat.tedu.addtodo.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.phelat.tedu.addtodo.R
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.entity.TodoEntity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_addtodo.saveTodo
import kotlinx.android.synthetic.main.fragment_addtodo.todoInput
import javax.inject.Inject

class AddTodoFragment : Fragment() {

    @Inject
    lateinit var dataSource: Writable<TodoEntity>

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_addtodo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveTodo.setOnClickListener {
            val todo = todoInput.text.toString()
            dataSource.write(TodoEntity(todo = todo))
        }
    }

}