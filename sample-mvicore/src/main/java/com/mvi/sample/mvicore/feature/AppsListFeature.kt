package com.mvi.sample.mvicore.feature

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

@Suppress("JoinDeclarationAndAssignment")
class AppsListFeature {

    private val feature = AppsListFeatureImpl()
    private val stateObservable = PublishSubject.create<AppsListState>()

    init {
        feature.subscribe(object : Observer<AppsListState> {
            override fun onSubscribe(d: Disposable) = Unit
            override fun onComplete() {
                stateObservable.onComplete()
            }
            override fun onNext(t: AppsListState) {
                stateObservable.onNext(t)
            }
            override fun onError(e: Throwable) {
                stateObservable.onError(e)
            }
        })
    }

    fun submit(wish: AppsListWish) {
        feature.accept(wish)
    }

    fun getState(): AppsListState {
        return feature.state
    }

    fun observeState(): Observable<AppsListState> {
        return stateObservable
    }

    fun destroy() {
        feature.dispose()
    }

}