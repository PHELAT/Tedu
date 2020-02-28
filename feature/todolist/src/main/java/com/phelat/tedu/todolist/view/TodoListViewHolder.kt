package com.phelat.tedu.todolist.view

import android.graphics.Paint
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.android.synthetic.main.item_todo.view.todoTitle

class TodoListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val todoTitle: AppCompatTextView = view.todoTitle

    fun bind(todoEntity: TodoEntity, onClickListener: (entity: TodoEntity) -> Unit) {
        todoTitle.text = todoEntity.todo
        if (todoEntity.isDone) {
            todoTitle.paintFlags = todoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            todoTitle.paintFlags = todoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        itemView.setOnClickListener {
            onClickListener.invoke(todoEntity)
        }
    }

}