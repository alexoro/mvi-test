package com.mvi.sample.mvikotlin.feature

import com.arkivanov.mvikotlin.core.store.Store

interface AppsListStore : Store<AppsListIntent, AppsListState, AppsListLabel>
interface AppsListIntent
interface AppsListLabel

data class AppsListState(
    val isLoading: Boolean = false,
    val list: List<String> = emptyList(),
    val initLoadError: Throwable? = null,
    val sendingLogs: Boolean = false
)

interface AppsListIntents {
    interface InitLoad {
        object Start : AppsListIntent
        object OnStart : AppsListIntent
        data class OnSuccess(val apps: List<String>) : AppsListIntent
        data class OnError(val error: Throwable) : AppsListIntent
    }
    interface SendLogs {
        data class Start(val logs: String) : AppsListIntent
        object Cancel : AppsListIntent
        object OnStart : AppsListIntent
        object OnComplete : AppsListIntent
    }
}