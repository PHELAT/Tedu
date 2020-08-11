package com.phelat.tedu.date.datasource

import com.phelat.tedu.datasource.Readable
import com.phelat.tedu.date.TeduDate
import com.phelat.tedu.date.di.qualifier.NowDate
import com.phelat.tedu.dependencyinjection.library.LibraryScope
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import java.text.DateFormat
import java.util.Date
import javax.inject.Inject

@LibraryScope
internal class DateDataSource @Inject constructor(
    @NowDate private val now: Lazy<@JvmSuppressWildcards LocalDate>,
    private val zoneId: Lazy<@JvmSuppressWildcards ZoneId>
) : DateDataSourceReadable {

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
            is TeduDate.HumanReadableDate -> {
                requireNotNull(DateFormat.getInstance().parse(input.date))
            }
        }
    }
}

typealias DateDataSourceReadable = Readable.IO<TeduDate, Date>