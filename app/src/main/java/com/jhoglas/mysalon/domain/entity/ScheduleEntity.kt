package com.jhoglas.mysalon.domain.entity

import java.time.LocalTime
import java.util.Date

data class ScheduleEntity(
    val userId: String = "",
    val establishmentId: String = "",
    val professionalId: String = "",
    val service: List<ServiceDomainEntity> = listOf(),
    val day: Date = Date(),
    val time: LocalTime = LocalTime.now(),
    val isAvailable: Boolean = false,
)
