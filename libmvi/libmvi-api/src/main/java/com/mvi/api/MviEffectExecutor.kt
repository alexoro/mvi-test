package com.mvi.api

interface MviEffectExecutor<Effect : MviEffect, Action : MviAction> {

    fun handle(effect: Effect, outActions: (Action) -> Unit)

    fun shutdown()

}