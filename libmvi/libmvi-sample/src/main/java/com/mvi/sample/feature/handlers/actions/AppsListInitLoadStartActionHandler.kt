package com.mvi.sample.feature.handlers.actions

import com.mvi.api.MviActionResult
import com.mvi.sample.feature.AppsListAction
import com.mvi.sample.feature.AppsListAction.AppsListInitLoadStartAction
import com.mvi.sample.feature.AppsListEffect
import com.mvi.sample.feature.AppsListEffect.AppsListInitLoadStartEffect
import com.mvi.sample.feature.AppsListState
import com.mvi.sample.feature.handlers.AppsListActionHandler

class AppsListInitLoadStartActionHandler : AppsListActionHandler {

    override fun handle(
        state: AppsListState,
        action: AppsListAction
    ): MviActionResult<AppsListState, AppsListEffect>? {
        if (action !is AppsListInitLoadStartAction) {
            return null
        }

        return MviActionResult(
            state = state.copy(isLoading = true, list = listOf("Loading"), initLoadError = null),
            effects = setOf(AppsListInitLoadStartEffect)
        )
    }

}