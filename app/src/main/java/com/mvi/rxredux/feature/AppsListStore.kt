package com.mvi.rxredux.feature


data class AppsListModel(
    val isLoading: Boolean = false,
    val list: List<String> = emptyList(),
    val initLoadError: Throwable? = null,
    val sendingLogs: Boolean = false
)


interface AppsListAction

interface AppsListActions {
    interface InitLoad {
        object Start : AppsListAction
        object OnStart : AppsListAction
        data class OnSuccess(val apps: List<String>) : AppsListAction
        data class OnError(val error: Throwable) : AppsListAction
    }
    interface SendLogs {
        data class Start(val logs: String) : AppsListAction
        object Cancel : AppsListAction
        object OnStart : AppsListAction
        object OnComplete : AppsListAction
    }
}