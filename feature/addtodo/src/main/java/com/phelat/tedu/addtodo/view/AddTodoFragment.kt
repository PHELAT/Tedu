package com.phelat.tedu.addtodo.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.phelat.tedu.addtodo.R
import com.phelat.tedu.addtodo.di.component.AddTodoComponent
import com.phelat.tedu.addtodo.view.calendar.CalendarSheet
import com.phelat.tedu.addtodo.viewmodel.AddTodoViewModel
import com.phelat.tedu.addtodo.viewmodel.DateViewModel
import com.phelat.tedu.androiddagger.inject
import com.phelat.tedu.designsystem.ext.makeLongSnackBar
import com.phelat.tedu.lifecycle.ViewModelFactory
import com.phelat.tedu.navigation.observeNavigation
import com.phelat.tedu.plaugin.FragmentPlugin
import com.phelat.tedu.plaugin.PlauginFragment
import com.phelat.tedu.plugins.SerializableImnPlugin
import com.phelat.tedu.sdkextensions.Visibility
import com.phelat.tedu.sdkextensions.hideKeyboard
import com.phelat.tedu.sdkextensions.showKeyboard
import com.phelat.tedu.todo.entity.TodoEntity
import kotlinx.android.synthetic.main.fragment_addtodo.dateClick
import kotlinx.android.synthetic.main.fragment_addtodo.saveTodo
import kotlinx.android.synthetic.main.fragment_addtodo.todoDate
import kotlinx.android.synthetic.main.fragment_addtodo.todoInput
import javax.inject.Inject

class AddTodoFragment : PlauginFragment(R.layout.fragment_addtodo) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val addTodoViewModel: AddTodoViewModel by viewModels { viewModelFactory }

    private val dateViewModel: DateViewModel by viewModels { viewModelFactory }

    private var calendarSheet: CalendarSheet? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject<AddTodoComponent>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveTodo.setOnClickListener {
            val todo = todoInput.text.toString()
            addTodoViewModel.onSaveTodoClicked(todo)
        }
        dateClick.setOnClickListener {
            dateViewModel.onSelectDateClick()
        }
        todoInput.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            addTodoViewModel.onTodoTextChange(text)
        })
        addTodoViewModel.apply {
            todoTextObservable.observe(viewLifecycleOwner, todoInput::setText)
            navigationObservable.observeNavigation(this@AddTodoFragment)
            snackBarObservable.observe(viewLifecycleOwner, ::showSnackBar)
            viewStateObservable.observe(viewLifecycleOwner, ::handleViewState)
        }
        dateViewModel.apply {
            todoDateObservable.observe(viewLifecycleOwner, todoDate::setText)
            todoDateSheetObservable.observe(viewLifecycleOwner, ::handleCalendarSheetVisibility)
        }
    }

    override fun onResume() {
        super.onResume()
        showKeyboard(inputToFocus = todoInput)
    }

    private fun showSnackBar(message: String) {
        hideKeyboard(todoInput.windowToken)
        requireActivity().makeLongSnackBar(message).show()
    }

    private fun handleCalendarSheetVisibility(visibility: Visibility) {
        when (visibility) {
            is Visibility.Visible -> {
                calendarSheet?.show()
                    ?: run {
                        calendarSheet = CalendarSheet(
                            requireContext(),
                            dateViewModel,
                            viewLifecycleOwner
                        )
                        handleCalendarSheetVisibility(visibility)
                    }
            }
            is Visibility.InVisible -> {
                calendarSheet?.dismiss()
            }
        }
    }

    private fun handleViewState(viewState: AddTodoViewState) {
        viewState.apply {
            saveTodo.isEnabled = isSaveTodoButtonEnabled
        }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(todoInput.windowToken)
    }

    override fun onDestroyView() {
        handleCalendarSheetVisibility(Visibility.InVisible)
        super.onDestroyView()
    }

    override fun plugins(): MutableList<FragmentPlugin> = mutableListOf(
        SerializableImnPlugin(fragment = this) { fragmentArgument: TodoEntity ->
            addTodoViewModel.onFragmentArgumentReceived(fragmentArgument)
            dateViewModel.onFragmentArgumentReceived(fragmentArgument)
        }
    )
}