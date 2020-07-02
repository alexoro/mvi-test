package com.mvi.api

import androidx.annotation.MainThread

@MainThread
class MviEffectHandlerComposer<Action, Effect>(
    vararg handlers: MviEffectHandler<Action, Effect>
) : MviEffectHandler<Action, Effect> {

    private val handlers = handlers.toList()

    override fun handle(effect: Effect, publisher: Publisher<Action>) {
        handlers.forEach {
            it.handle(effect, publisher)
        }
    }

    override fun shutdown() {
        handlers.forEach {
            it.shutdown()
        }
    }

}