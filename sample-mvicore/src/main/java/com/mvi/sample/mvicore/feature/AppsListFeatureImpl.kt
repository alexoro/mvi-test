package com.mvi.sample.mvicore.feature

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class AppsListFeatureImpl : ActorReducerFeature<AppsListWish, AppsListEffect, AppsListState, Nothing>(
    initialState = AppsListState(),
    actor = ActorImpl(),
    reducer = ReducerImpl()
) {

    class ActorImpl : Actor<AppsListState, AppsListWish, AppsListEffect> {

        private val sendLogsCancellation = PublishSubject.create<Unit>()

        override fun invoke(state: AppsListState, wish: AppsListWish): Observable<AppsListEffect> = when (wish) {
            is AppsListWishes.InitLoad.Start -> initLoad()
            is AppsListWishes.SendLogs.Start -> startSendLogs()
            is AppsListWishes.SendLogs.Cancel -> stopSendLogs()
            else -> Observable.empty()
        }

        private fun initLoad(): Observable<AppsListEffect> {
            return Single
                .timer(3, TimeUnit.SECONDS)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .map { AppsListEffects.InitLoad.OnSuccess(listOf("Success")) as AppsListEffect }
                .startWith(AppsListEffects.InitLoad.OnStart)
                .onErrorReturn { AppsListEffects.InitLoad.OnError(it) }
        }

        private fun startSendLogs(): Observable<AppsListEffect> {
            return Single
                .timer(3, TimeUnit.SECONDS)
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .map { AppsListEffects.SendLogs.OnComplete as AppsListEffect }
                .startWith(AppsListEffects.SendLogs.OnStart)
                .onErrorReturn { AppsListEffects.SendLogs.OnComplete }
                .takeUntil(sendLogsCancellation)
        }

        private fun stopSendLogs(): Observable<AppsListEffect> {
            sendLogsCancellation.onNext(Unit)
            return Observable.empty()
        }

    }

    class ReducerImpl : Reducer<AppsListState, AppsListEffect> {
        override fun invoke(state: AppsListState, effect: AppsListEffect): AppsListState = when (effect) {
            is AppsListEffects.InitLoad.OnStart -> state.copy(isLoading = true, list = listOf("Loading"), initLoadError = null)
            is AppsListEffects.InitLoad.OnSuccess -> state.copy(isLoading = false, list = effect.apps, initLoadError = null)
            is AppsListEffects.InitLoad.OnError -> state.copy(isLoading = false, list = emptyList(), initLoadError = effect.error)
            is AppsListEffects.SendLogs.OnStart -> state.copy(sendingLogs = true)
            is AppsListEffects.SendLogs.OnComplete -> state.copy(sendingLogs = false)
            else -> state
        }
    }
}
