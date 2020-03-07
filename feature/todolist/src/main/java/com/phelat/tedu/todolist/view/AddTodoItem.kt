package com.phelat.tedu.todolist.view

import com.phelat.tedu.todolist.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

class AddTodoItem(
    private val onAddTodoClickListener: () -> Unit
) : Item(Long.MAX_VALUE) {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.setOnClickListener {
            onAddTodoClickListener.invoke()
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_add_todo
    }
}