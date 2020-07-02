package com.mvi.sample.feature.handlers.actions

import com.mvi.api.MviActionResult
import com.mvi.sample.feature.AppsListAction
import com.mvi.sample.feature.AppsListAction.AppsListSendLogsOnErrorAction
import com.mvi.sample.feature.AppsListEffect
import com.mvi.sample.feature.AppsListState
import com.mvi.sample.feature.handlers.AppsListActionHandler

class AppsListSendLogsOnErrorActionHandler : AppsListActionHandler {

    override fun handle(
        state: AppsListState,
        action: AppsListAction
    ): MviActionResult<AppsListState, AppsListEffect>? {
        if (action !is AppsListSendLogsOnErrorAction) {
            return null
        }

        return MviActionResult(
            state = state.copy(sendingLogs = false))
    }

}