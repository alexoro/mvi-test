package com.mvi.sample.mobius.feature

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
            is AppsListEvents.InitLoad.Start -> onEventInitLoad(model, event)
            is AppsListEvents.InitLoad.OnSuccess -> onEventInitLoad(model, event)
            is AppsListEvents.InitLoad.OnError -> onEventInitLoad(model, event)
            is AppsListEvents.SendLogs.Start -> onEventSendLogs(model, event)
            is AppsListEvents.SendLogs.Cancel -> onEventSendLogs(model, event)
            is AppsListEvents.SendLogs.OnComplete -> onEventSendLogs(model, event)
            is AppsListEvents.SendLogs.OnError -> onEventSendLogs(model, event)
            else -> throw UnsupportedOperationException("Unknown event: $event")
        }
    }

    // ------------------------------------------------------------------------------------

    private fun onEventInitLoad(
        model: AppsListModel,
        event: AppsListEvents.InitLoad.Start
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
        event: AppsListEvents.SendLogs.Start
    ): Next<AppsListModel, AppsListEffect> {
        return next(
            model.copy(sendingLogs = true),
            Effects.effects(AppsListEffects.SendLogs.Start(event.logs)))
    }

    private fun onEventSendLogs(
        model: AppsListModel,
        event: AppsListEvents.SendLogs.Cancel
    ): Next<AppsListModel, AppsListEffect> {
        return next(
            model.copy(sendingLogs = false),
            Effects.effects(AppsListEffects.SendLogs.Cancel))
    }

    private fun onEventSendLogs(
        model: AppsListModel,
        event: AppsListEvents.SendLogs.OnComplete
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