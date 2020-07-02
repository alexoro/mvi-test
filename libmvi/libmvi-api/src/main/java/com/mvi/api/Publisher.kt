package com.mvi.api

import androidx.annotation.AnyThread

@AnyThread
interface Publisher<Action> {

    fun publish(action: Action)

}