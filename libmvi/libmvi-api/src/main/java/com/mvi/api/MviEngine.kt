package com.mvi.api

import io.reactivex.Observable

interface MviEngine<State : MviState, Action : MviAction, Effect : MviEffect> {

    fun submit(intent: Action)

    fun getState(): State
    fun observeState(): Observable<State>

    fun shutdown()

}