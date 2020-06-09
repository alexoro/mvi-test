package com.mvi.mobius

import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

class AppsListEffectHandler : Connectable<AppsListEffects, AppsListEvents> {

    private var initLoadDisposable: Disposable? = null

    override fun connect(output: Consumer<AppsListEvents>): Connection<AppsListEffects> {
        return object : Connection<AppsListEffects> {
            override fun accept(value: AppsListEffects) {
                when (value) {
                    is AppsListEffects.InitLoadStart -> {
                        initLoadDisposable = initLoad(output)
                    }
                }
            }
            override fun dispose() {
                initLoadDisposable?.dispose()
            }
        }
    }

    private fun initLoad(output: Consumer<AppsListEvents>): Disposable {
        return Single
            .timer(3, TimeUnit.SECONDS)
            .subscribeBy(
                onSuccess = { output.accept(AppsListEvents.InitLoad.OnSuccess(listOf("Success"))) },
                onError = { output.accept(AppsListEvents.InitLoad.OnError(it)) }
            )
    }

}