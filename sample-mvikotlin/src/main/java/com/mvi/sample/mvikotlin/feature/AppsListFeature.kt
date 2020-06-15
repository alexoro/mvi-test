package com.mvi.sample.mvikotlin.feature

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.arkivanov.mvikotlin.rx.Observer
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

@Suppress("JoinDeclarationAndAssignment")
class AppsListFeature {

    private val store = AppsListStoreFactory(DefaultStoreFactory).create()
    private val stateObservable = PublishSubject.create<AppsListState>()

    init {
        store.states(object : Observer<AppsListState> {
            override fun onNext(value: AppsListState) {
                stateObservable.onNext(value)
            }
            override fun onComplete() {
                stateObservable.onComplete()
            }
        })
    }

    fun submit(intent: AppsListIntent) {
        store.accept(intent)
    }

    fun getState(): AppsListState {
        return store.state
    }

    fun observeState(): Observable<AppsListState> {
        return stateObservable
    }

    fun destroy() {
        store.dispose()
    }

}