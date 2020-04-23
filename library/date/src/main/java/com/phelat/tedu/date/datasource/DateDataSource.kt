package com.phelat.tedu.date.datasource

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import java.util.Date
import javax.inject.Inject

@LibraryScope
class DateDataSource @Inject constructor(
    private val now: Lazy<@JvmSuppressWildcards LocalDate>,
    private val zoneId: Lazy<@JvmSuppressWildcards ZoneId>
) : Readable.IO<TeduDate, Date> {

    override fun read(input: TeduDate): Date {
        return when (input) {
            is TeduDate.Today -> {
                val today = now.value.atStartOfDay()
                    .atZone(zoneId.value)
                    .toInstant()
                    .toEpochMilli()
                Date(today)
            }
            is TeduDate.Tomorrow -> {
                val tomorrow = now.value.atStartOfDay()
                    .plusDays(1)
                    .atZone(zoneId.value)
                    .toInstant()
                    .toEpochMilli()
                Date(tomorrow)
            }
        }
    }
}