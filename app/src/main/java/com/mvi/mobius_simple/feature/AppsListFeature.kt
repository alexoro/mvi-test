package com.mvi.mobius_simple.feature

import com.mvi.mobius_simple.base.MviFeature
import com.mvi.imengine.ImEngine
import com.mvi.imengine.OnCacheInvalidateEvent
import com.mvi.mobius_simple.MobiusFactory
import com.spotify.mobius.MobiusLoop
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

@Suppress("JoinDeclarationAndAssignment")
class AppsListFeature(
    private val imEngine: ImEngine
) : MviFeature<AppsListEvent, AppsListModel> {

    private val mobius: MobiusLoop<AppsListModel, AppsListEvent, AppsListEffect>
    private val model: Observable<AppsListModel>
    private var imEngineEventsObserveDisposable: Disposable? = null

    init {
        val result = MobiusFactory.create(
            model = AppsListModel(),
            updater = AppsListUpdater(),
            effectHandler = AppsListEffectHandler()
        )
        mobius = result.mobius
        model = result.model

        imEngineEventsObserveDisposable = imEngine
            .observeEvents()
            .subscribeBy(onNext = {
                when (it) {
                    is OnCacheInvalidateEvent -> {
                        //submit(...)
                    }
                }
            })
    }

    override fun submit(event: AppsListEvent) {
        mobius.dispatchEvent(event)
    }

    override fun getModel(): AppsListModel {
        return mobius.mostRecentModel!!
    }

    override fun observeModel(): Observable<AppsListModel> {
        return model
    }

    override fun destroy() {
        imEngineEventsObserveDisposable?.dispose()
        mobius.dispose()
    }

}