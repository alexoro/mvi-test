package com.mvi.rxredux.feature

import com.freeletics.rxredux.reduxStore
import com.mvi.imengine.ImEngine
import com.mvi.rxredux.feature.sideeffects.AppsListInitLoadSideEffects
import com.mvi.rxredux.feature.sideeffects.AppsListSendLogsSideEffects
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class AppsListFeature(
    private val imEngine: ImEngine
) {

    private val actionsSubject = PublishSubject.create<AppsListAction>()
    private val actionsDisposable: Disposable

    private val initialModel = AppsListModel()
    private val modelSubject = BehaviorSubject.createDefault(initialModel)

    private val imEngineEventsDisposable: Disposable

    init {
        actionsDisposable = actionsSubject
            .reduxStore(
                initialState = initialModel,
                sideEffects = listOf(
                    AppsListInitLoadSideEffects::start,
                    AppsListSendLogsSideEffects::send
                ),
                reducer = AppsListReducer())
            .distinctUntilChanged()
            .subscribeBy(
                onNext = {
                    modelSubject.onNext(it)
                })

        imEngineEventsDisposable = imEngine
            .observeEvents()
            .subscribe()
    }

    fun submit(event: AppsListAction) {
        actionsSubject.onNext(event)
    }

    fun getModel(): AppsListModel {
        return modelSubject.value!!
    }

    fun observeModel(): Observable<AppsListModel> {
        return modelSubject
    }

    fun destroy() {
        actionsDisposable.dispose()
        imEngineEventsDisposable.dispose()
    }

}