package com.phelat.tedu.todolist.view

import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import com.phelat.tedu.todo.entity.TodoEntity
import com.phelat.tedu.todolist.R
import com.phelat.tedu.todolist.databinding.ItemTodoBinding
import com.xwray.groupie.viewbinding.BindableItem

class TodoListItem(
    private val todoEntity: TodoEntity,
    private val onClickListener: (entity: TodoEntity) -> Unit,
    private val onLongClickListener: (entity: TodoEntity) -> Unit
) : BindableItem<ItemTodoBinding>(todoEntity.todoId) {

    override fun bind(viewBinding: ItemTodoBinding, position: Int) {
        viewBinding.todoTitle.text = todoEntity.todo
        if (todoEntity.isDone) {
            viewBinding.todoTitle.setTextColor(
                ContextCompat.getColor(
                    viewBinding.todoTitle.context,
                    R.color.text_hint_color
                )
            )
            viewBinding.todoTitle.paintFlags = viewBinding.todoTitle.paintFlags or
                    Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            viewBinding.todoTitle.setTextColor(
                ContextCompat.getColor(
                    viewBinding.todoTitle.context,
                    R.color.text_primary_color
                )
            )
            viewBinding.todoTitle.paintFlags = viewBinding.todoTitle.paintFlags and
                    Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
        viewBinding.root.setOnClickListener {
            onClickListener.invoke(todoEntity)
        }
        viewBinding.root.setOnLongClickListener {
            onLongClickListener.invoke(todoEntity)
            true
        }
    }

    override fun initializeViewBinding(view: View): ItemTodoBinding {
        return ItemTodoBinding.bind(view)
    }

    override fun getLayout(): Int {
        return R.layout.item_todo
    }
}