package com.phelat.tedu.todo.mapper

import com.phelat.tedu.dependencyinjection.common.CommonScope
import com.phelat.tedu.mapper.Mapper
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import java.util.Date
import javax.inject.Inject

@CommonScope
internal class DateToLocalDate @Inject constructor() : Mapper<Date, LocalDate> {

    override fun mapFirstToSecond(first: Date): LocalDate {
        return Instant.ofEpochMilli(first.time)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    override fun mapSecondToFirst(second: LocalDate): Date {
        return second.atTime(LocalTime.now())
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
            .run { Date(this) }
    }
}