package com.mvi.api

interface MviActionExecutor<State : MviState> {

    data class Result<State>(
        val state: State? = null,
        val effects: Collection<MviEffect>? = null
    )

    fun apply(state: State, action: MviAction): Result<State>

}