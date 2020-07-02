package com.mvi.api

import androidx.annotation.AnyThread

@AnyThread
data class MviActionResult<State, Effect>(
    val state: State? = null,
    val effects: Set<Effect>? = null
)