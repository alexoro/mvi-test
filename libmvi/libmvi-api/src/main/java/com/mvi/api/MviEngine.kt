package com.mvi.api

import androidx.annotation.AnyThread
import io.reactivex.Observable

@AnyThread
interface MviEngine<State, Action, Effect> {

    fun submit(action: Action)

    fun getState(): State
    fun addStateObserver(observer: MviStateObserver<State>)
    fun removeStateObserver(observer: MviStateObserver<State>)
    fun observeState(): Observable<State>

    fun shutdown()

}