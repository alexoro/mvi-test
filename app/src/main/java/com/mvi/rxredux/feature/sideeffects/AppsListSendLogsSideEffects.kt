package com.mvi.rxredux.feature.sideeffects

import com.freeletics.rxredux.StateAccessor
import com.mvi.rxredux.feature.AppsListAction
import com.mvi.rxredux.feature.AppsListActions
import com.mvi.rxredux.feature.AppsListModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

internal object AppsListSendLogsSideEffects {

    fun send(
        actions: Observable<AppsListAction>,
        state: StateAccessor<AppsListModel>
    ) : Observable<AppsListAction> {
        val processingObservable = actions
            .ofType(AppsListActions.SendLogs.Start::class.java)
            .switchMap {
                sendLogs()
                    .toObservable()
                    .map { AppsListActions.SendLogs.OnComplete as AppsListAction }
                    .onErrorReturn { AppsListActions.SendLogs.OnComplete }
                    .startWith(AppsListActions.SendLogs.OnStart)
                    .takeUntil(actions.ofType(AppsListActions.SendLogs.Cancel::class.java))
            }
        val cancelObservable = actions
            .ofType(AppsListActions.SendLogs.Cancel::class.java)
            .map { AppsListActions.SendLogs.OnComplete }

        return Observable.merge(
            processingObservable,
            cancelObservable
        )
    }

    private fun sendLogs(): Single<Boolean> {
        return Single
            .fromCallable {
                try {
                    Thread.sleep(3000)
                    return@fromCallable true
                } catch (ignored: InterruptedException) {
                    // Ignore
                    return@fromCallable false
                } catch (th: Throwable) {
                    throw th
                }
            }
            .subscribeOn(Schedulers.io())
    }

}