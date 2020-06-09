package com.mvi.mobius

import com.spotify.mobius.Effects
import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import com.spotify.mobius.Update

class AppsListLogic : Update<AppsListModel, AppsListEvents, AppsListEffects> {

    override fun update(
        model: AppsListModel,
        event: AppsListEvents
    ): Next<AppsListModel, AppsListEffects> {
        return when (event) {
            is AppsListEvents.InitLoad.OnStart -> onEventInitLoad(model, event)
            is AppsListEvents.InitLoad.OnSuccess -> onEventInitLoad(model, event)
            is AppsListEvents.InitLoad.OnError -> onEventInitLoad(model, event)
            is AppsListEvents.SendLogs -> onEventSendLogs(model, event)
        }
    }

    // ------------------------------------------------------------------------------------

    private fun onEventInitLoad(
        model: AppsListModel,
        event: AppsListEvents.InitLoad.OnStart
    ): Next<AppsListModel, AppsListEffects> {
        return next(
            model.copy(isLoading = true, list = listOf("Loading"), initLoadError = null),
            Effects.effects(AppsListEffects.InitLoadStart))
    }

    private fun onEventInitLoad(
        model: AppsListModel,
        event: AppsListEvents.InitLoad.OnSuccess
    ): Next<AppsListModel, AppsListEffects> {
        return next(
            model.copy(isLoading = false, list = event.apps, initLoadError = null))
    }

    private fun onEventInitLoad(
        model: AppsListModel,
        event: AppsListEvents.InitLoad.OnError
    ): Next<AppsListModel, AppsListEffects> {
        return next(
            model.copy(isLoading = false, list = emptyList(), initLoadError = event.error))
    }

    // ------------------------------------------------------------------------------------

    private fun onEventSendLogs(
        model: AppsListModel,
        event: AppsListEvents.SendLogs
    ): Next<AppsListModel, AppsListEffects> {
        return dispatch(
            Effects.effects(AppsListEffects.SendLogs(event.logs)))
    }

}