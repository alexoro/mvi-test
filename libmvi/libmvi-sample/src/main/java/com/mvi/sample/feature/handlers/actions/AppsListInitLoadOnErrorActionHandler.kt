package com.mvi.sample.feature.handlers.actions

import com.mvi.api.MviActionResult
import com.mvi.sample.feature.AppsListAction
import com.mvi.sample.feature.AppsListAction.AppsListInitLoadOnErrorAction
import com.mvi.sample.feature.AppsListEffect
import com.mvi.sample.feature.AppsListState
import com.mvi.sample.feature.handlers.AppsListActionHandler

class AppsListInitLoadOnErrorActionHandler : AppsListActionHandler {

    override fun handle(
        state: AppsListState,
        action: AppsListAction
    ): MviActionResult<AppsListState, AppsListEffect>? {
        if (action !is AppsListInitLoadOnErrorAction) {
            return null
        }

        return MviActionResult(
            state = state.copy(isLoading = false, list = emptyList(), initLoadError = action.error))
    }

}