package com.phelat.tedu.todolist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phelat.tedu.todolist.R

class TodoListAdapter(
    private val todos: MutableList<String>
) : RecyclerView.Adapter<TodoListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val todo = todos[position]
        holder.bind(todo)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

}