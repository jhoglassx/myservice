package com.jhoglas.mysalon.utils.extensions

import java.util.Calendar
import java.util.Date

fun Date.getMonthFromDate(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return when (calendar.get(Calendar.MONTH)) {
        1 -> "Jan"
        2 -> "Fev"
        3 -> "Mar"
        4 -> "Abr"
        5 -> "Mai"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Ago"
        9 -> "Set"
        10 -> "Out"
        11 -> "Nov"
        12 -> "Dez"
        else -> "Mtf"
    }
}

fun Date.getDayFromDate(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK)
}
