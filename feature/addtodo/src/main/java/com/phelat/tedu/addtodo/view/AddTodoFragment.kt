package com.phelat.tedu.addtodo.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.phelat.tedu.addtodo.databinding.FragmentAddtodoBinding
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
import com.phelat.tedu.plugins.invoke
import com.phelat.tedu.plugins.plugin
import com.phelat.tedu.plugins.viewBinding
import com.phelat.tedu.sdkextensions.Visibility
import com.phelat.tedu.sdkextensions.hideKeyboard
import com.phelat.tedu.sdkextensions.showKeyboard
import com.phelat.tedu.todo.entity.TodoEntity
import javax.inject.Inject

class AddTodoFragment : PlauginFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val addTodoViewModel: AddTodoViewModel by viewModels { viewModelFactory }

    private val dateViewModel: DateViewModel by viewModels { viewModelFactory }

    private var calendarSheet: CalendarSheet? = null

    private val viewBinding = viewBinding { inflater, viewGroup ->
        FragmentAddtodoBinding.inflate(inflater, viewGroup, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject<AddTodoComponent>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding().apply {
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
    }

    override fun onResume() {
        super.onResume()
        showKeyboard(inputToFocus = viewBinding().todoInput)
    }

    private fun showSnackBar(message: String) {
        hideKeyboard(viewBinding().todoInput.windowToken)
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
            viewBinding().saveTodo.isEnabled = isSaveTodoButtonEnabled
        }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard(viewBinding().todoInput.windowToken)
    }

    override fun onDestroyView() {
        handleCalendarSheetVisibility(Visibility.InVisible)
        super.onDestroyView()
    }

    override fun plugins(): MutableList<FragmentPlugin> = mutableListOf(
        SerializableImnPlugin(fragment = this) { fragmentArgument: TodoEntity ->
            addTodoViewModel.onFragmentArgumentReceived(fragmentArgument)
            dateViewModel.onFragmentArgumentReceived(fragmentArgument)
        },
        viewBinding.plugin
    )
}