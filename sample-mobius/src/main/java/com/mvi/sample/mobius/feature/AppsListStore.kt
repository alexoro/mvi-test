package com.mvi.sample.mobius.feature

interface AppsListEffect
interface AppsListEvent

data class AppsListModel(
    val isLoading: Boolean = false,
    val list: List<String> = emptyList(),
    val initLoadError: Throwable? = null,
    val sendingLogs: Boolean = false
)

interface AppsListEvents {
    interface InitLoad {
        object OnStart : AppsListEvent
        data class OnSuccess(val apps: List<String>) : AppsListEvent
        data class OnError(val error: Throwable) : AppsListEvent
    }
    interface SendLogs {
        data class OnStart(val logs: String) : AppsListEvent
        object OnCancel : AppsListEvent
        object OnSuccess : AppsListEvent
        data class OnError(val error: Throwable) : AppsListEvent
    }
}

interface AppsListEffects {
    interface InitLoad {
        object Start : AppsListEffect
    }
    interface SendLogs {
        class Start(val logs: String) : AppsListEffect
        object Cancel : AppsListEffect
    }
}