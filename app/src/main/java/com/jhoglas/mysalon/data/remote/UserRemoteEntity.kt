package com.jhoglas.mysalon.data.remote

import com.jhoglas.mysalon.domain.entity.UserDomainEntity

data class UserRemoteEntity(
    val name: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val dateCreate: String? = null,
    val dateUpdate: String? = null,
    val image: String? = null,
)

fun UserRemoteEntity.toDomain() = UserDomainEntity(
    name = name,
    email = email,
    phoneNumber = phoneNumber,
    dateCreate = dateCreate,
    dateUpdate = dateUpdate,
    image = image
)
