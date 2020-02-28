package com.phelat.tedu.todolist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todolist.R

class TodoListAdapter(
    private val onClickListener: (entity: TodoEntity) -> Unit,
    private val todos: MutableList<TodoEntity> = mutableListOf()
) : RecyclerView.Adapter<TodoListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val todo = todos[position]
        holder.bind(todo, onClickListener)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    fun update(newTodos: List<TodoEntity>) {
        val todoListDiff = TodoListDiff(newTodos, todos)
        val diffResult = DiffUtil.calculateDiff(todoListDiff)
        todos.clear()
        todos.addAll(newTodos)
        diffResult.dispatchUpdatesTo(this)
    }

}