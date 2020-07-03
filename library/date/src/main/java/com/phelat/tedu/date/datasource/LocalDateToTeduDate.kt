package com.phelat.tedu.date.datasource

import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.date.di.qualifier.NowDate
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import com.phelat.tedu.mapper.Mapper
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import java.text.DateFormat
import javax.inject.Inject

@LibraryScope
class LocalDateToTeduDate @Inject constructor(
    @NowDate private val nowDate: Lazy<@JvmSuppressWildcards LocalDate>,
    private val zoneId: Lazy<@JvmSuppressWildcards ZoneId>
) : Mapper<LocalDate, TeduDate> {

    override fun mapFirstToSecond(first: LocalDate): TeduDate {
        return when {
            first == nowDate.value -> {
                TeduDate.Today
            }
            isSelectedDateTomorrow(nowDate.value, first) -> {
                TeduDate.Tomorrow
            }
            else -> {
                TeduDate.HumanReadableDate("${first.year}/${first.monthValue}/${first.dayOfMonth}")
            }
        }
    }

    override fun mapSecondToFirst(second: TeduDate): LocalDate {
        return when (second) {
            is TeduDate.Today -> nowDate.value
            is TeduDate.Tomorrow -> nowDate.value.plusDays(1)
            is TeduDate.HumanReadableDate -> {
                val date = requireNotNull(DateFormat.getInstance().parse(second.date))
                Instant.ofEpochMilli(date.time).atZone(zoneId.value).toLocalDate()
            }
        }
    }

    private fun isSelectedDateTomorrow(today: LocalDate, selectedDate: LocalDate): Boolean {
        return selectedDate.minusDays(1).dayOfYear == today.dayOfYear
    }
}