package com.jhoglas.mysalon.domain.entity

import com.jhoglas.mysalon.data.remote.UserRemoteEntity

data class UserDomainEntity(
    val name: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val dateCreate: String? = null,
    val dateUpdate: String? = null,
    val image: String? = null,
)

fun UserDomainEntity.toRemote() = UserRemoteEntity(
    name = name,
    email = email,
    phoneNumber = phoneNumber,
    dateCreate = dateCreate,
    dateUpdate = dateUpdate,
    image = image
)
