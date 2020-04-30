package com.phelat.tedu.addtodo.datasource

import com.phelat.tedu.addtodo.di.scope.AddTodoScope
import com.phelat.tedu.addtodo.entity.SelectedDate
import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.datasource.Writable
import com.phelat.tedu.date.di.qualifier.NowDate
import org.threeten.bp.LocalDate
import javax.inject.Inject

@AddTodoScope
class SelectedDateDataSource @Inject constructor(
    @NowDate private val nowDate: Lazy<LocalDate>
) : Readable<SelectedDate>, Writable<SelectedDate> {

    private var selectedDate: SelectedDate = SelectedDate(nowDate.value)

    override fun read(): SelectedDate {
        return selectedDate
    }

    override fun write(input: SelectedDate) {
        selectedDate = input
    }
}