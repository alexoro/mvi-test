package com.mvi.mobius

sealed class AppsListEffects {

    object InitLoadStart : AppsListEffects()
    data class SendLogs(val logs: String) : AppsListEffects()

}