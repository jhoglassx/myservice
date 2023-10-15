package com.jhoglas.mysalon.ui.entity

data class ScreenState(
    val content: Any? = null,
    val state: State = State.EMPTY,
    val message: String = ""
)
