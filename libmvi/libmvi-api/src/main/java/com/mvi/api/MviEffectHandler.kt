package com.mvi.api

import androidx.annotation.MainThread

@MainThread
interface MviEffectHandler<Action, Effect> {

    fun handle(effect: Effect, publisher: Publisher<Action>)
    fun shutdown()

}