package com.mvi.mobius

import com.mvi.api.*
import com.spotify.mobius.Mobius
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Next
import com.spotify.mobius.Update
import com.spotify.mobius.android.AndroidLogger
import com.spotify.mobius.android.runners.MainThreadWorkRunner
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class MobiusMviEngine<State : MviState, Action : MviAction, Effect : MviEffect>(
    private val initialState: State,
    //private val actions: List<Class<out MviAction>>,
    //private val actionExecutors: Map<out Class<MviAction>, MviActionExecutor<MviState, MviAction, MviEffect>>,
    private val actionsExecutor: List<MviActionExecutor<State>>
) : MviEngine<State, Action, Effect> {

    val x = mapOf(
        SampleAction1::class.java to SampleAction1Executor(),
        SampleAction2::class.java to SampleAction2Executor()
    )

    private val actionExecutors2: Map<Class<out MviAction>, MviActionExecutor<*, *, *>>

    private val loop: MobiusLoop<State, Action, Effect>
    private val state = BehaviorSubject.create<State>()

    init {
        loop = Mobius
            .loop(updater, effectHandler)
            .eventRunner { MainThreadWorkRunner.create() }
            .effectRunner { MainThreadWorkRunner.create() }
            .logger(AndroidLogger("QQQQ"))
            .startFrom(state)

        actionExecutors2 =  mapOf(
            SampleAction1::class.java to SampleAction1Executor(),
            SampleAction2::class.java to SampleAction2Executor()
        )
    }

    private inner class UpdaterImpl : Update<State, Action, Effect> {
        override fun update(model: State, event: Action): Next<State, Effect> {
            actionsExecutor

            actionExecutors.forEach { (actionClass, executor) ->
                if (event::class.java.isAssignableFrom(actionClass)) {
                    executor.apply(model, event)
                }
            }
        }
    }

    override fun submit(intent: Action) {
        loop.dispatchEvent(intent)
    }

    override fun getState(): State {
        return loop.mostRecentModel!!
    }

    override fun observeState(): Observable<State> {
        return state
    }

    override fun shutdown() {
        loop.dispose()
    }

}