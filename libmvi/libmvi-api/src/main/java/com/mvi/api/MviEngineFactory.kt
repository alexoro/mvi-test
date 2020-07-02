package com.mvi.api

import androidx.annotation.AnyThread

@AnyThread
interface MviEngineFactory {

    fun <State, Action, Effect> create(
        initialState: State,
        actionHandler: MviActionHandler<State, Action, Effect>,
        effectHandler: MviEffectHandler<Action, Effect>
    ): MviEngine<State, Action, Effect>

}