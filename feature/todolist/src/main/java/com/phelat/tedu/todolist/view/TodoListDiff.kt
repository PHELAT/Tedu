package com.phelat.tedu.todolist.view

import androidx.recyclerview.widget.DiffUtil
import com.phelat.tedu.todo.entity.TodoEntity

class TodoListDiff(
    private val newList: List<TodoEntity>,
    private val oldList: List<TodoEntity>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.todoId == newItem.todoId
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return (oldItem.todo == newItem.todo) && (oldItem.isDone == newItem.isDone)
    }
}