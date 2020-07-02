package com.mvi.api

import androidx.annotation.MainThread

@MainThread
interface MviStateObserver<State> {

    fun onStateUpdate(state: State)

}