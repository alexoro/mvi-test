package com.mvi.sample.mobius.feature

interface AppsListEffect
interface AppsListEvent

data class AppsListModel(
    val isLoading: Boolean = false,
    val list: List<String> = emptyList(),
    val initLoadError: Throwable? = null,
    val sendingLogs: Boolean = false
)

/**
 * Общий контракт - все, что начинается на On - внутренние Intent, результаты выполнения SideEffect.
 * Все остальное - это публичные Intent, которые могут вызывать внешние товарищи (UI).
 */
interface AppsListEvents {
    interface InitLoad {
        object Start : AppsListEvent
        data class OnSuccess(val apps: List<String>) : AppsListEvent
        data class OnError(val error: Throwable) : AppsListEvent
    }
    interface SendLogs {
        data class Start(val logs: String) : AppsListEvent
        object Cancel : AppsListEvent
        object OnComplete : AppsListEvent
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