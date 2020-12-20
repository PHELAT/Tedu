package com.phelat.tedu.todolist.view

import android.view.View
import com.phelat.tedu.todolist.R
import com.phelat.tedu.todolist.databinding.ItemAddTodoBinding
import com.xwray.groupie.viewbinding.BindableItem

class AddTodoItem(
    private val onAddTodoClickListener: () -> Unit
) : BindableItem<ItemAddTodoBinding>(Long.MAX_VALUE) {

    override fun bind(viewBinding: ItemAddTodoBinding, position: Int) {
        viewBinding.viewRoot.setOnClickListener {
            onAddTodoClickListener.invoke()
        }
    }

    override fun initializeViewBinding(view: View): ItemAddTodoBinding {
        return ItemAddTodoBinding.bind(view)
    }

    override fun getLayout(): Int {
        return R.layout.item_add_todo
    }
}