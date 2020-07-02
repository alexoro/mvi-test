package com.mvi.impl.mobius

import androidx.annotation.AnyThread
import com.mvi.api.*
import com.spotify.mobius.*
import com.spotify.mobius.android.runners.MainThreadWorkRunner
import com.spotify.mobius.functions.Consumer
import io.reactivex.Observable
import java.util.concurrent.CopyOnWriteArrayList

@AnyThread
class MobiusMviEngine<State, Action, Effect> (
    initialState: State,
    private val actionHandler: MviActionHandler<State, Action, Effect>,
    private val effectHandler: MviEffectHandler<Action, Effect>,
    private val logger: MviLogger<State, Action, Effect>? = null
) : MviEngine<State, Action, Effect> {

    private val loop: MobiusLoop<State, Action, Effect>
    private val stateObservers = CopyOnWriteArrayList<MviStateObserver<State>>()

    init {
        val actionHandlerImpl = UpdaterImpl()
        val effectHandlerImpl = EffectHandlerImpl()

        // Default logic is just to log error
        MobiusHooks.setErrorHandler {
                error -> throw error
        }

        loop = Mobius
            .loop(actionHandlerImpl, effectHandlerImpl)
            .eventRunner { MainThreadWorkRunner.create() }
            .effectRunner { MainThreadWorkRunner.create() }
            .startFrom(initialState)

        loop.observe(object : Consumer<State> {
            private var oldState: State? = null
            override fun accept(value: State) {
                if (oldState !== value) {
                    oldState = value
                    stateObservers.forEach { observer ->
                        observer.onStateUpdate(value)
                    }
                }
            }
        })
    }

    @Suppress("FoldInitializerAndIfToElvis", "UNCHECKED_CAST")
    private inner class UpdaterImpl : Update<State, Action, Effect> {
        override fun update(model: State, event: Action): Next<State, Effect> {
            logger?.beforeUpdate(model, event)

            val result = try {
                actionHandler.handle(model, event)
            } catch (th: Throwable) {
                logger?.afterUpdate(model, event, th)
                throw th
            }
            logger?.afterUpdate(model, event, result as MviActionResult<State, Effect>)

            if (result == null) {
                return Next.noChange()
            }

            val (newState, sideEffects) = result
            return when {
                newState != null && sideEffects != null && sideEffects.isNotEmpty() -> Next.next(newState, sideEffects)
                newState != null -> Next.next(newState)
                sideEffects != null && sideEffects.isNotEmpty() -> Next.dispatch(sideEffects)
                else -> Next.noChange()
            }
        }
    }

    private inner class EffectHandlerImpl : Connectable<Effect, Action> {
        override fun connect(output: Consumer<Action>): Connection<Effect> {
            val publisher = object : Publisher<Action> {
                override fun publish(action: Action) {
                    output.accept(action)
                }
            }
            return object : Connection<Effect> {
                override fun accept(value: Effect) {
                    effectHandler.handle(value, publisher)
                }
                override fun dispose() {
                    effectHandler.shutdown()
                }
            }
        }
    }

    override fun submit(action: Action) {
        loop.dispatchEvent(action)
    }

    override fun getState(): State {
        return loop.mostRecentModel!!
    }

    override fun addStateObserver(observer: MviStateObserver<State>) {
        stateObservers.add(observer)
    }

    override fun removeStateObserver(observer: MviStateObserver<State>) {
        stateObservers.remove(observer)
    }

    override fun observeState(): Observable<State> {
        return Observable.create { emitter ->
            val observer = object : MviStateObserver<State> {
                override fun onStateUpdate(state: State) {
                    emitter.onNext(state)
                }
            }
            emitter.setCancellable {
                this.removeStateObserver(observer)
            }
            this.addStateObserver(observer)
        }
    }

    override fun shutdown() {
        loop.dispose()
    }

}