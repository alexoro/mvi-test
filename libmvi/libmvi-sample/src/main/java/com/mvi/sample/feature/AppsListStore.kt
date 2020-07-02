package com.mvi.sample.feature

data class AppsListState(
    val isLoading: Boolean = false,
    val list: List<String> = emptyList(),
    val initLoadError: Throwable? = null,
    val sendingLogs: Boolean = false
)

sealed class AppsListAction {

    object AppsListInitLoadStartAction : AppsListAction()
    data class AppsListInitLoadOnSuccessAction(val apps: List<String>) : AppsListAction()
    data class AppsListInitLoadOnErrorAction(val error: Throwable) : AppsListAction()

    data class AppsListSendLogsStartAction(val logs: String) : AppsListAction()
    object AppsListSendLogsCancelAction : AppsListAction()
    object AppsListSendLogsOnCompleteAction : AppsListAction()
    data class AppsListSendLogsOnErrorAction(val error: Throwable) : AppsListAction()

}

sealed class AppsListEffect {

    object AppsListInitLoadStartEffect : AppsListEffect()

    class AppsListSendLogsStartEffect(val logs: String) : AppsListEffect()
    object AppsListSendLogsCancelEffect : AppsListEffect()

}