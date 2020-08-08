package com.phelat.tedu.todolist.view

import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todolist.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_todo.todoTitle

class TodoListItem(
    private val todoEntity: TodoEntity,
    private val onClickListener: (entity: TodoEntity) -> Unit,
    private val onLongClickListener: (entity: TodoEntity) -> Unit
) : Item(todoEntity.todoId.toLong()) {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.todoTitle.text = todoEntity.todo
        if (todoEntity.isDone) {
            viewHolder.todoTitle.setTextColor(
                ContextCompat.getColor(
                    viewHolder.todoTitle.context,
                    R.color.text_hint_color
                )
            )
            viewHolder.todoTitle.paintFlags = viewHolder.todoTitle.paintFlags or
                    Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            viewHolder.todoTitle.setTextColor(
                ContextCompat.getColor(
                    viewHolder.todoTitle.context,
                    R.color.text_primary_color
                )
            )
            viewHolder.todoTitle.paintFlags = viewHolder.todoTitle.paintFlags and
                    Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        viewHolder.itemView.setOnClickListener {
            onClickListener.invoke(todoEntity)
        }
        viewHolder.itemView.setOnLongClickListener {
            onLongClickListener.invoke(todoEntity)
            true
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_todo
    }
}