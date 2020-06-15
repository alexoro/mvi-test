package com.mvi.sample.mvicore.feature

interface AppsListWish
interface AppsListEffect

data class AppsListState(
    val isLoading: Boolean = false,
    val list: List<String> = emptyList(),
    val initLoadError: Throwable? = null,
    val sendingLogs: Boolean = false
)

interface AppsListWishes {
    interface InitLoad {
        object Start : AppsListWish
    }
    interface SendLogs {
        data class Start(val logs: String) : AppsListWish
        object Cancel : AppsListWish
    }
}

interface AppsListEffects {
    interface InitLoad {
        object OnStart : AppsListEffect
        data class OnSuccess(val apps: List<String>) : AppsListEffect
        data class OnError(val error: Throwable) : AppsListEffect
    }
    interface SendLogs {
        object OnStart : AppsListEffect
        object OnComplete : AppsListEffect
    }
}