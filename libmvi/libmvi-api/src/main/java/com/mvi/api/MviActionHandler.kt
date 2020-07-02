package com.mvi.api

import androidx.annotation.MainThread

@MainThread
interface MviActionHandler<State, Action, Effect> {

    fun handle(state: State, action: Action): MviActionResult<State, Effect>?

}