package com.mvi.mobius_simple.feature

import com.spotify.mobius.Effects
import com.spotify.mobius.Next
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update

class AppsListUpdater : Update<AppsListModel, AppsListEvent, AppsListEffect> {

    override fun update(
        model: AppsListModel,
        event: AppsListEvent
    ): Next<AppsListModel, AppsListEffect> {
        return when (event) {
            is AppsListEvents.InitLoad.OnStart -> onEventInitLoad(model, event)
            is AppsListEvents.InitLoad.OnSuccess -> onEventInitLoad(model, event)
            is AppsListEvents.InitLoad.OnError -> onEventInitLoad(model, event)
            is AppsListEvents.SendLogs.OnStart -> onEventSendLogs(model, event)
            is AppsListEvents.SendLogs.OnCancel -> onEventSendLogs(model, event)
            is AppsListEvents.SendLogs.OnSuccess -> onEventSendLogs(model, event)
            is AppsListEvents.SendLogs.OnError -> onEventSendLogs(model, event)
            else -> throw UnsupportedOperationException("Unknown event: $event")
        }
    }

    // ------------------------------------------------------------------------------------

    private fun onEventInitLoad(
        model: AppsListModel,
        event: AppsListEvents.InitLoad.OnStart
    ): Next<AppsListModel, AppsListEffect> {
        return next(
            model.copy(isLoading = true, list = listOf("Loading"), initLoadError = null),
            Effects.effects(AppsListEffects.InitLoad.Start))
    }

    private fun onEventInitLoad(
        model: AppsListModel,
        event: AppsListEvents.InitLoad.OnSuccess
    ): Next<AppsListModel, AppsListEffect> {
        return next(
            model.copy(isLoading = false, list = event.apps, initLoadError = null))
    }

    private fun onEventInitLoad(
        model: AppsListModel,
        event: AppsListEvents.InitLoad.OnError
    ): Next<AppsListModel, AppsListEffect> {
        return next(
            model.copy(isLoading = false, list = emptyList(), initLoadError = event.error))
    }

    // ------------------------------------------------------------------------------------

    private fun onEventSendLogs(
        model: AppsListModel,
        event: AppsListEvents.SendLogs.OnStart
    ): Next<AppsListModel, AppsListEffect> {
        return next(
            model.copy(sendingLogs = true),
            Effects.effects(AppsListEffects.SendLogs.Start(event.logs)))
    }

    private fun onEventSendLogs(
        model: AppsListModel,
        event: AppsListEvents.SendLogs.OnCancel
    ): Next<AppsListModel, AppsListEffect> {
        return next(
            model.copy(sendingLogs = false),
            Effects.effects(AppsListEffects.SendLogs.Cancel))
    }

    private fun onEventSendLogs(
        model: AppsListModel,
        event: AppsListEvents.SendLogs.OnSuccess
    ): Next<AppsListModel, AppsListEffect> {
        return next(
            model.copy(sendingLogs = false))
    }

    private fun onEventSendLogs(
        model: AppsListModel,
        event: AppsListEvents.SendLogs.OnError
    ): Next<AppsListModel, AppsListEffect> {
        return next(
            model.copy(sendingLogs = false))
    }

}