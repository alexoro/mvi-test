package com.mvi.sample.mobius.feature

import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

class AppsListEffectHandler : Connectable<AppsListEffect, AppsListEvent> {

    private var initLoadDisposable: Disposable? = null
    private var sendLogsDisposable: Disposable? = null

    override fun connect(output: Consumer<AppsListEvent>): Connection<AppsListEffect> {
        return object : Connection<AppsListEffect> {
            override fun accept(value: AppsListEffect) {
                when (value) {
                    is AppsListEffects.InitLoad.Start -> {
                        initLoadDisposable = initLoad(output)
                    }
                    is AppsListEffects.SendLogs.Start -> {
                        sendLogsDisposable = sendLogs(output)
                    }
                    is AppsListEffects.SendLogs.Cancel -> {
                        sendLogsDisposable?.dispose()
                        sendLogsDisposable = null
                    }
                }
            }
            override fun dispose() {
                initLoadDisposable?.dispose()
                sendLogsDisposable?.dispose()
            }
        }
    }

    private fun initLoad(output: Consumer<AppsListEvent>): Disposable {
        return Single
            .timer(3, TimeUnit.SECONDS)
            .subscribeBy(
                onSuccess = { output.accept(
                    AppsListEvents.InitLoad.OnSuccess(
                        listOf("Success")
                    )
                )},
                onError = { output.accept(
                    AppsListEvents.InitLoad.OnError(
                        it
                    )
                )})
    }

    private fun sendLogs(output: Consumer<AppsListEvent>): Disposable {
        return Single
            .timer(3, TimeUnit.SECONDS)
            .subscribeBy(
                onSuccess = { output.accept(
                    AppsListEvents.SendLogs.OnSuccess
                )},
                onError = { output.accept(
                    AppsListEvents.SendLogs.OnError(it)
                )}
            )
    }

}