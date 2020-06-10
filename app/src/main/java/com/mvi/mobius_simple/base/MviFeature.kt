package com.mvi.mobius_simple.base

import io.reactivex.Observable

interface MviFeature<Event : MviEvent, Model : MviModel> {

    fun submit(event: Event)

    fun getModel(): Model
    fun observeModel(): Observable<Model>

    fun destroy()

}