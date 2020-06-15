package com.mvi.sample.mobius.feature

import com.mvi.sample.mobius.MobiusFactory
import com.spotify.mobius.MobiusLoop
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

@Suppress("JoinDeclarationAndAssignment")
class AppsListFeature {

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
    }

    fun submit(event: AppsListEvent) {
        mobius.dispatchEvent(event)
    }

    fun getModel(): AppsListModel {
        return mobius.mostRecentModel!!
    }

    fun observeModel(): Observable<AppsListModel> {
        return model
    }

    fun destroy() {
        imEngineEventsObserveDisposable?.dispose()
        mobius.dispose()
    }

}