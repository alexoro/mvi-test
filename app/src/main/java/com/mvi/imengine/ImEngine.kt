package com.mvi.imengine

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ImEngine {

    private val eventsSubject = PublishSubject.create<ImEngineEvent>()

    fun observeEvents(): Observable<ImEngineEvent> {
        return eventsSubject
    }

}