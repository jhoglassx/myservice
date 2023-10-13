package com.jhoglas.mysalon.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfessionalDomainEntity(
    val id: String,
    val establishmentId: Int,
    val name: String,
    val photo: String,
    val services: List<String>,
) : Parcelable

fun getProfessionals(): List<ProfessionalDomainEntity> = listOf(
    ProfessionalDomainEntity(
        id = "1",
        establishmentId = 1,
        name = "John Doe1",
        photo = "https://picsum.photos/150/150",
        services = listOf("Haircut", "Beard", "Shave")
    ),
    ProfessionalDomainEntity(
        id = "2",
        establishmentId = 2,
        name = "John Doe2",
        photo = "https://picsum.photos/151/151",
        services = listOf("Haircut", "Beard", "Shave")
    ),
    ProfessionalDomainEntity(
        id = "3",
        establishmentId = 2,
        name = "John Doe3",
        photo = "https://picsum.photos/152/152",
        services = listOf("Haircut", "Beard", "Shave")
    ),
    ProfessionalDomainEntity(
        id = "4",
        establishmentId = 2,
        name = "John Doe4",
        photo = "https://picsum.photos/153/153",
        services = listOf("Haircut", "Beard", "Shave")
    ),
    ProfessionalDomainEntity(
        id = "5",
        establishmentId = 2,
        name = "John Doe5",
        photo = "https://picsum.photos/154/154",
        services = listOf("Haircut", "Beard", "Shave")
    )
)
