package com.phelat.tedu.addtodo.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.phelat.tedu.addtodo.R
import com.phelat.tedu.addtodo.view.calendar.CalendarSheet
import com.phelat.tedu.addtodo.viewmodel.AddTodoViewModel
import kotlinx.android.synthetic.main.fragment_addtodo.dateClick
import kotlinx.android.synthetic.main.fragment_addtodo.saveTodo
import kotlinx.android.synthetic.main.fragment_addtodo.todoDate
import kotlinx.android.synthetic.main.fragment_addtodo.todoInput
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTodoFragment : Fragment(R.layout.fragment_addtodo) {

    private val addTodoViewModel by viewModel<AddTodoViewModel>()

    private var calendarSheet: CalendarSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addTodoViewModel.onBundleReceive(arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveTodo.setOnClickListener {
            val todo = todoInput.text.toString()
            addTodoViewModel.onSaveTodoClicked(todo)
            // TODO: Move to view model
            findNavController().navigateUp()
        }
        dateClick.setOnClickListener {
            addTodoViewModel.onSelectDateClick()
        }
        addTodoViewModel.apply {
            todoTextObservable.observe(viewLifecycleOwner, todoInput::setText)
            todoDateObservable.observe(viewLifecycleOwner, todoDate::setText)
            todoDateSheetObservable.observe(viewLifecycleOwner) { showCalendarSheet() }
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