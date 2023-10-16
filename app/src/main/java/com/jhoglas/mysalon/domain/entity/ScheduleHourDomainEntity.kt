package com.jhoglas.mysalon.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class ScheduleHourDomainEntity(
    val day: Int,
    val start: LocalTime,
    val end: LocalTime,
    val isSelected: Boolean = false
) : Parcelable

fun getScheduleHours(): List<ScheduleHourDomainEntity> = listOf(
    ScheduleHourDomainEntity(
        day = 1,
        start = LocalTime.of(9, 0),
        end = LocalTime.of(10, 0)
    ),
    ScheduleHourDomainEntity(
        day = 1,
        start = LocalTime.of(10, 0),
        end = LocalTime.of(11, 0)
    ),
    ScheduleHourDomainEntity(
        day = 6,
        start = LocalTime.of(11, 0),
        end = LocalTime.of(12, 0)
    ),
    ScheduleHourDomainEntity(
        day = 2,
        start = LocalTime.of(12, 0),
        end = LocalTime.of(13, 0)
    ),
    ScheduleHourDomainEntity(
        day = 2,
        start = LocalTime.of(13, 0),
        end = LocalTime.of(14, 0)
    ),
    ScheduleHourDomainEntity(
        day = 2,
        start = LocalTime.of(14, 0),
        end = LocalTime.of(15, 0)
    ),
    ScheduleHourDomainEntity(
        day = 3,
        start = LocalTime.of(15, 0),
        end = LocalTime.of(16, 0)
    ),
    ScheduleHourDomainEntity(
        day = 4,
        start = LocalTime.of(16, 0),
        end = LocalTime.of(17, 0)
    )
)
