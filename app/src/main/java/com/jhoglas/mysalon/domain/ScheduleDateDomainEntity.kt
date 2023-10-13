package com.jhoglas.mysalon.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import java.util.Date

@Parcelize
data class ScheduleDateDomainEntity(
    val professionalId: String,
    val date: Date,
    val isSelected: Boolean = false,
) : Parcelable

fun getScheduleDays() = listOf(
    ScheduleDateDomainEntity(
        professionalId = "1",
        date = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.MONTH, 10)
            set(Calendar.YEAR, 2023)
        }.time

    ),
    ScheduleDateDomainEntity(
        professionalId = "1",
        date = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 2)
            set(Calendar.MONTH, 10)
            set(Calendar.YEAR, 2023)
        }.time

    ),
    ScheduleDateDomainEntity(
        professionalId = "1",
        date = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 3)
            set(Calendar.MONTH, 10)
            set(Calendar.YEAR, 2023)
        }.time

    ),
    ScheduleDateDomainEntity(
        professionalId = "4",
        date = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 4)
            set(Calendar.MONTH, 10)
            set(Calendar.YEAR, 2023)
        }.time

    ),
    ScheduleDateDomainEntity(
        professionalId = "4",
        date = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 5)
            set(Calendar.MONTH, 10)
            set(Calendar.YEAR, 2023)
        }.time

    ),
    ScheduleDateDomainEntity(
        professionalId = "6",
        date = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 6)
            set(Calendar.MONTH, 10)
            set(Calendar.YEAR, 2023)
        }.time

    ),
    ScheduleDateDomainEntity(
        professionalId = "7",
        date = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 7)
            set(Calendar.MONTH, 10)
            set(Calendar.YEAR, 2023)
        }.time

    ),
    ScheduleDateDomainEntity(
        professionalId = "8",
        date = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 8)
            set(Calendar.MONTH, 10)
            set(Calendar.YEAR, 2023)
        }.time

    ),
    ScheduleDateDomainEntity(
        professionalId = "3",
        date = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 9)
            set(Calendar.MONTH, 10)
            set(Calendar.YEAR, 2023)
        }.time

    ),
    ScheduleDateDomainEntity(
        professionalId = "10",
        date = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 10)
            set(Calendar.MONTH, 10)
            set(Calendar.YEAR, 2023)
        }.time
    )
)
