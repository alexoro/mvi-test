package com.mvi.mobius_simple

import com.mvi.base.MviEffect
import com.mvi.base.MviEvent
import com.mvi.base.MviModel
import com.spotify.mobius.Connectable
import com.spotify.mobius.Mobius
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Update
import com.spotify.mobius.android.AndroidLogger
import com.spotify.mobius.android.runners.MainThreadWorkRunner
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

object MobiusFactory {

    data class Result<Model : MviModel, Event : MviEvent, Effect : MviEffect>(
        val mobius: MobiusLoop<Model, Event, Effect>,
        val model: Observable<Model>
    )

    fun <Model : MviModel, Event : MviEvent, Effect : MviEffect>create(
        model: Model,
        updater: Update<Model, Event, Effect>,
        effectHandler: Connectable<Effect, Event>
    ) : Result<Model, Event, Effect> {
        val loop = Mobius
            .loop(updater, effectHandler)
            .eventRunner { MainThreadWorkRunner.create() }
            .effectRunner { MainThreadWorkRunner.create() }
            .logger(AndroidLogger("QQQQ"))
            .startFrom(model)

        val subject = BehaviorSubject.create<Model>()
        loop.observe {
            subject.onNext(it)
        }

        return Result(loop, subject)
    }

}