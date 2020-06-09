package com.mvi.mobius

sealed class AppsListEvents {

    sealed class InitLoad : AppsListEvents() {
        object OnStart : InitLoad()
        data class OnSuccess(val apps: List<String>) : InitLoad()
        data class OnError(val error: Throwable) : InitLoad()
    }

    data class SendLogs(val logs: String) : AppsListEvents()

}