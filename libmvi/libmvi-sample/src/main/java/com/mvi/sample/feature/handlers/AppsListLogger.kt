package com.mvi.sample.feature.handlers

import android.util.Log
import com.mvi.api.MviActionResult
import com.mvi.api.MviLogger
import com.mvi.sample.feature.AppsListAction
import com.mvi.sample.feature.AppsListEffect
import com.mvi.sample.feature.AppsListState

class AppsListLogger : MviLogger<AppsListState, AppsListAction, AppsListEffect> {

    override fun beforeUpdate(state: AppsListState, action: AppsListAction) {
        Log.d("QQQQ", "#beforeUpdate: action=$action, state=$state")
    }

    override fun afterUpdate(
        state: AppsListState,
        action: AppsListAction,
        result: MviActionResult<AppsListState, AppsListEffect>?
    ) {
        Log.d("QQQQ", "#afterUpdate: action=$action, state=$state, result=$result")
    }

    override fun afterUpdate(state: AppsListState, action: AppsListAction, exception: Throwable) {
        Log.e("QQQQ", "#afterUpdate: action=$action, state=$state, exception=$exception")
    }

}