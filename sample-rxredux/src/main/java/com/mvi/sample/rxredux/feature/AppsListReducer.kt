package com.mvi.sample.rxredux.feature

import android.util.Log
import com.freeletics.rxredux.Reducer

internal class AppsListReducer : Reducer<AppsListModel, AppsListAction> {

    override fun invoke(model: AppsListModel, action: AppsListAction): AppsListModel {
        val new = when (action) {
            is AppsListActions.InitLoad.OnStart -> model.copy(isLoading = true, list = listOf("Loading"), initLoadError = null)
            is AppsListActions.InitLoad.OnSuccess -> model.copy(isLoading = false, list = action.apps, initLoadError = null)
            is AppsListActions.InitLoad.OnError -> model.copy(isLoading = false, list = emptyList(), initLoadError = action.error)
            is AppsListActions.SendLogs.OnStart -> model.copy(sendingLogs = true)
            is AppsListActions.SendLogs.OnComplete -> model.copy(sendingLogs = false)
            else -> model
        }
        if (model != new) {
            Log.e("QQQQ", "$new")
        }
        return new
    }

}