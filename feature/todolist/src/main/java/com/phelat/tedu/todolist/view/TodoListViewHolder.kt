package com.phelat.tedu.todolist.view

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.todoTitle

class TodoListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val todoTitle: AppCompatTextView = view.todoTitle

    fun bind(todoTitleText: String) {
        todoTitle.text = todoTitleText
    }

}