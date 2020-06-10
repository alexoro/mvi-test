package com.mvi.rxredux.feature.sideeffects

import com.freeletics.rxredux.StateAccessor
import com.mvi.rxredux.feature.AppsListAction
import com.mvi.rxredux.feature.AppsListActions
import com.mvi.rxredux.feature.AppsListModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

internal object AppsListInitLoadSideEffects {

    fun start(
        actions: Observable<AppsListAction>,
        state: StateAccessor<AppsListModel>
    ) : Observable<AppsListAction> {
        return actions
            .ofType(AppsListActions.InitLoad.Start::class.java)
            .flatMap {
                return@flatMap initLoad()
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .map { AppsListActions.InitLoad.OnSuccess(it) as AppsListAction }
                    .onErrorReturn { AppsListActions.InitLoad.OnError(it) }
                    .startWith(AppsListActions.InitLoad.OnStart)
            }
    }

    private fun initLoad(): Single<List<String>> {
        return Single
            .fromCallable {
                try {
                    Thread.sleep(3000)
                    return@fromCallable listOf("Success")
                } catch (ignored: InterruptedException) {
                    // Ignore
                    return@fromCallable emptyList<String>()
                } catch (th: Throwable) {
                    throw th
                }
            }
            .subscribeOn(Schedulers.io())
    }

}