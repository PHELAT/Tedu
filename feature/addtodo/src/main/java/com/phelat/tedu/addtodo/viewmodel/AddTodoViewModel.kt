package com.phelat.tedu.addtodo.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phelat.tedu.coroutines.Dispatcher
import com.phelat.tedu.datasource.Updatable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.todo.constant.TodoConstant
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.coroutines.launch

class AddTodoViewModel(
    private val dispatcher: Dispatcher,
    private val todoDataSourceWritable: Writable.Suspendable<TodoEntity>,
    private val todoDataSourceUpdatable: Updatable.Suspendable<TodoEntity>
) : ViewModel() {

    private val _todoTextObservable = MutableLiveData<String>()
    val todoTextObservable: LiveData<String> = _todoTextObservable

    private var todoForEdit: TodoEntity? = null

    fun onBundleReceive(bundle: Bundle?) {
        val todoForEdit = bundle?.getSerializable(TodoConstant.TODO_FOR_EDIT)
        if (todoForEdit is TodoEntity) {
            this.todoForEdit = todoForEdit
            _todoTextObservable.value = todoForEdit.todo
        }
    }

    fun onSaveTodoClicked(todo: String) {
        viewModelScope.launch(context = dispatcher.iO) {
            if (todoForEdit != null) {
                todoDataSourceUpdatable.update(requireNotNull(todoForEdit).copy(todo))
            } else {
                todoDataSourceWritable.write(TodoEntity(todo = todo))
            }
        }
    }
}