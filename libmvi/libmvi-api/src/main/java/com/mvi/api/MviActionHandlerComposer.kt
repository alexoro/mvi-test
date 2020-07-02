package com.mvi.api

import androidx.annotation.MainThread

@MainThread
class MviActionHandlerComposer<State, Action, Effect>(
    vararg handlers: MviActionHandler<State, Action, Effect>
) : MviActionHandler<State, Action, Effect> {

    private val handlers = handlers.toList()

    override fun handle(state: State, action: Action): MviActionResult<State, Effect> {
        var result: MviActionResult<State, Effect>? = null
        for (handler in handlers) {
            result = handler.handle(state, action)
            if (result != null) {
                break
            }
        }
        return result ?: throw IllegalStateException("No handlers for action: $action")
    }

}