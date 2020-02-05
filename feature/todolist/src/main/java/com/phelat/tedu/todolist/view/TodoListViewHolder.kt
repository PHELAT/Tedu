package com.phelat.tedu.todolist.view

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.android.synthetic.main.item_todo.view.todoTitle

class TodoListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val todoTitle: AppCompatTextView = view.todoTitle

    fun bind(todoEntity: TodoEntity) {
        todoTitle.text = todoEntity.todo
    }

}