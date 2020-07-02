package com.mvi.sample.feature.handlers.effects

import com.mvi.api.Publisher
import com.mvi.sample.feature.AppsListAction
import com.mvi.sample.feature.AppsListEffect
import com.mvi.sample.feature.handlers.AppsListEffectHandler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

class AppsListInitLoadEffectHandler : AppsListEffectHandler {

    private var initLoadDisposable: Disposable? = null

    override fun handle(effect: AppsListEffect, publisher: Publisher<AppsListAction>) {
        if (effect is AppsListEffect.AppsListInitLoadStartEffect) {
            initLoadDisposable = initLoad(publisher)
        }
    }

    override fun shutdown() {
        initLoadDisposable?.dispose()
    }

    private fun initLoad(publisher: Publisher<AppsListAction>): Disposable {
        return Single
            .timer(3, TimeUnit.SECONDS)
            .subscribeBy(
                onSuccess = {
                    publisher.publish(AppsListAction.AppsListInitLoadOnSuccessAction(listOf("Success")))
                },
                onError = {
                    publisher.publish(AppsListAction.AppsListInitLoadOnErrorAction(it))
                })
    }

}