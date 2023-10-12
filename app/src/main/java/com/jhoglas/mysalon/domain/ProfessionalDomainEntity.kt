package com.jhoglas.mysalon.domain

import android.os.Parcelable
import com.jhoglas.mysalon.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfessionalDomainEntity(
    val id: Int,
    val name: String,
    val photo: Int,
    val services: List<String>,
) : Parcelable

fun getProfessionals(): List<ProfessionalDomainEntity> = listOf(
    ProfessionalDomainEntity(
        id = 1,
        name = "John Doe1",
        photo = R.drawable.cabelereiro,
        services = listOf("Haircut", "Beard", "Shave")
    ),
    ProfessionalDomainEntity(
        id = 2,
        name = "John Doe2",
        photo = R.drawable.cabelereiro,
        services = listOf("Haircut", "Beard", "Shave")
    )
)
