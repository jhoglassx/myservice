package com.jhoglas.mysalon.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceDomainEntity(
    val title: String = "",
    var isSelected: Boolean = false
) : Parcelable
