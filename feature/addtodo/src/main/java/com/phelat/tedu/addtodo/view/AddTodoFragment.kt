package com.phelat.tedu.addtodo.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.phelat.tedu.addtodo.R
import com.phelat.tedu.addtodo.di.component.AddTodoComponent
import com.phelat.tedu.addtodo.viewmodel.AddTodoViewModel
import com.phelat.tedu.daggerandroid.Injector
import kotlinx.android.synthetic.main.fragment_addtodo.saveTodo
import kotlinx.android.synthetic.main.fragment_addtodo.todoInput
import javax.inject.Inject

class AddTodoFragment : Fragment(R.layout.fragment_addtodo) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val addTodoViewModel by viewModels<AddTodoViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addTodoViewModel.onBundleReceive(requireArguments())
    }

    override fun onAttach(context: Context) {
        Injector.inject(AddTodoComponent::class,this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveTodo.setOnClickListener {
            val todo = todoInput.text.toString()
            addTodoViewModel.onSaveTodoClicked(todo)
            findNavController().navigateUp()
        }
        addTodoViewModel.apply {
            todoTextObservable.observe(viewLifecycleOwner, todoInput::setText)
        }
    }

}