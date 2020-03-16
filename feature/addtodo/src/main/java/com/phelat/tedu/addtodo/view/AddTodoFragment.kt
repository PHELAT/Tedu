package com.phelat.tedu.addtodo.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.phelat.tedu.addtodo.R
import com.phelat.tedu.addtodo.di.component.AddTodoComponent
import com.phelat.tedu.addtodo.view.calendar.CalendarSheet
import com.phelat.tedu.addtodo.viewmodel.AddTodoViewModel
import com.phelat.tedu.androiddagger.inject
import com.phelat.tedu.uiview.observeNavigation
import kotlinx.android.synthetic.main.fragment_addtodo.dateClick
import kotlinx.android.synthetic.main.fragment_addtodo.saveTodo
import kotlinx.android.synthetic.main.fragment_addtodo.todoDate
import kotlinx.android.synthetic.main.fragment_addtodo.todoInput
import javax.inject.Inject

class AddTodoFragment : Fragment(R.layout.fragment_addtodo) {

    @Inject
    lateinit var addTodoViewModelFactory: ViewModelProvider.Factory

    private val addTodoViewModel: AddTodoViewModel by viewModels { addTodoViewModelFactory }

    private var calendarSheet: CalendarSheet? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject<AddTodoComponent>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addTodoViewModel.onBundleReceive(arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveTodo.setOnClickListener {
            val todo = todoInput.text.toString()
            addTodoViewModel.onSaveTodoClicked(todo)
        }
        dateClick.setOnClickListener {
            addTodoViewModel.onSelectDateClick()
        }
        addTodoViewModel.apply {
            todoTextObservable.observe(viewLifecycleOwner, todoInput::setText)
            todoDateObservable.observe(viewLifecycleOwner, todoDate::setText)
            todoDateSheetObservable.observe(viewLifecycleOwner) { showCalendarSheet() }
            navigationObservable.observeNavigation(this@AddTodoFragment)
        }
    }

    private fun showCalendarSheet() {
        calendarSheet?.show()
            ?: run {
                calendarSheet = CalendarSheet(
                    requireContext(),
                    addTodoViewModel::onDateSelect,
                    addTodoViewModel::getSelectedDate
                )
                showCalendarSheet()
            }
    }

    override fun onDestroyView() {
        if (calendarSheet?.isShowing == true) {
            calendarSheet?.dismiss()
        }
        super.onDestroyView()
    }
}