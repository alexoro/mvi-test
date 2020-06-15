package com.mvi.sample.rxredux.feature

import com.freeletics.rxredux.reduxStore
import com.mvi.sample.rxredux.feature.sideeffects.AppsListInitLoadSideEffects
import com.mvi.sample.rxredux.feature.sideeffects.AppsListSendLogsSideEffects
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class AppsListFeature {

    private val actionsSubject = PublishSubject.create<AppsListAction>()
    private val actionsDisposable: Disposable

    private val initialModel = AppsListModel()
    private val modelSubject = BehaviorSubject.createDefault(initialModel)

    init {
        actionsDisposable = actionsSubject
            .reduxStore(
                initialState = initialModel,
                sideEffects = listOf(
                    AppsListInitLoadSideEffects::start,
                    AppsListSendLogsSideEffects::send
                ),
                reducer = AppsListReducer()
            )
            .distinctUntilChanged()
            .subscribeBy(
                onNext = {
                    modelSubject.onNext(it)
                })
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
    }

}