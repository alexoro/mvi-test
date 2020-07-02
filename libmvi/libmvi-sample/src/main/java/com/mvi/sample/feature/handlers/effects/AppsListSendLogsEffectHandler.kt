package com.mvi.sample.feature.handlers.effects

import com.mvi.api.Publisher
import com.mvi.sample.feature.AppsListAction
import com.mvi.sample.feature.AppsListEffect
import com.mvi.sample.feature.handlers.AppsListEffectHandler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

class AppsListSendLogsEffectHandler : AppsListEffectHandler {

    private var sendLogsDisposable: Disposable? = null

    override fun handle(effect: AppsListEffect, publisher: Publisher<AppsListAction>) {
        when (effect) {
            is AppsListEffect.AppsListSendLogsStartEffect -> {
                sendLogsDisposable = sendLogs(publisher)
            }
            is AppsListEffect.AppsListSendLogsCancelEffect -> {
                sendLogsDisposable?.dispose()
                sendLogsDisposable = null
            }
        }
    }

    override fun shutdown() {
        sendLogsDisposable?.dispose()
    }

    private fun sendLogs(publisher: Publisher<AppsListAction>): Disposable {
        return Single
            .timer(3, TimeUnit.SECONDS)
            .subscribeBy(
                onSuccess = {
                    publisher.publish(AppsListAction.AppsListSendLogsOnCompleteAction)
                },
                onError = {
                    publisher.publish(AppsListAction.AppsListSendLogsOnErrorAction(it))
                }
            )
    }

}