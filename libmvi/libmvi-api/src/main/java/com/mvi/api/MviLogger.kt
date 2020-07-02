package com.mvi.api

import androidx.annotation.AnyThread

/**
 * Interface for logging update calls.
 */
@AnyThread
interface MviLogger<State, Action, Effect> {

    /**
     * This method mustn't block, as it'll hinder the loop from running. It will be called on the
     * same thread as the update function.
     * @param state the state that will be passed to the update function
     * @param action the action that will be passed to the update function
     */
    fun beforeUpdate(state: State, action: Action)

    /**
     * This method mustn't block, as it'll hinder the loop from running. It will be called on the
     * same thread as the update function.
     * @param state the state that was passed to update
     * @param action the action that was passed to update
     * @param result the [MviActionResult] that update returned
     */
    fun afterUpdate(state: State, action: Action, result: MviActionResult<State, Effect>?)

    /**
     * Called if the update invocation throws an exception.
     * @param state the state object that led to the exception
     * @param action the action that was passed to update
     * @param exception the thrown exception
     */
    fun afterUpdate(state: State, action: Action, exception: Throwable)

}