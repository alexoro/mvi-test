package com.mvi.sample.mvikotlin.feature

import com.arkivanov.mvikotlin.core.store.*
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit

internal class AppsListStoreFactory(private val storeFactory: StoreFactory) {

    fun create(): AppsListStore {
        return object : AppsListStore, Store<AppsListIntent, AppsListState, AppsListLabel> by storeFactory.create(
            name = "AppsListStore",
            initialState = AppsListState(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl) {

        }
    }

    private object ReducerImpl : Reducer<AppsListState, AppsListIntent> {
        override fun AppsListState.reduce(intent: AppsListIntent): AppsListState {
            return when (intent) {
                is AppsListIntents.InitLoad.OnStart -> this.copy(isLoading = true, list = listOf("Loading"), initLoadError = null)
                is AppsListIntents.InitLoad.OnSuccess -> this.copy(isLoading = false, list = intent.apps, initLoadError = null)
                is AppsListIntents.InitLoad.OnError -> this.copy(isLoading = false, list = emptyList(), initLoadError = intent.error)
                is AppsListIntents.SendLogs.OnStart -> this.copy(sendingLogs = true)
                is AppsListIntents.SendLogs.OnComplete -> this.copy(sendingLogs = false)
                else -> this
            }
        }
    }

    private class ExecutorImpl : Executor<AppsListIntent, Nothing, AppsListState, AppsListIntent, AppsListLabel> {

        private lateinit var callbacks: Executor.Callbacks<AppsListState, AppsListIntent, AppsListLabel>

        private var initLoadDisposable: Disposable? = null
        private var sendLogsDisposable: Disposable? = null

        override fun init(callbacks: Executor.Callbacks<AppsListState, AppsListIntent, AppsListLabel>) {
            this.callbacks = callbacks
        }

        override fun handleIntent(intent: AppsListIntent) {
            when (intent) {
                is AppsListIntents.InitLoad.Start -> {
                    initLoadDisposable = initLoad()
                }
                is AppsListIntents.SendLogs.Start -> {
                    sendLogsDisposable = sendLogs()
                }
                is AppsListIntents.SendLogs.Cancel -> {
                    sendLogsDisposable?.dispose()
                    sendLogsDisposable = null
                }
            }
        }

        override fun dispose() {
            initLoadDisposable?.dispose()
            sendLogsDisposable?.dispose()
        }

        private fun initLoad(): Disposable {
            return Single
                .timer(3, TimeUnit.SECONDS)
                .subscribeBy(
                    onSuccess = { callbacks.onResult(
                        AppsListIntents.InitLoad.OnSuccess(listOf("Success"))
                    )},
                    onError = { callbacks.onResult(
                        AppsListIntents.InitLoad.OnError(it)
                    )})
        }

        private fun sendLogs(): Disposable {
            return Single
                .timer(3, TimeUnit.SECONDS)
                .subscribeBy(
                    onSuccess = { callbacks.onResult(
                        AppsListIntents.SendLogs.OnComplete
                    )},
                    onError = { callbacks.onResult(
                        AppsListIntents.SendLogs.OnComplete
                    )}
                )
        }

    }

}

