package com.mvi.sample.mobius

import com.spotify.mobius.Connectable
import com.spotify.mobius.Mobius
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Update
import com.spotify.mobius.android.AndroidLogger
import com.spotify.mobius.android.runners.MainThreadWorkRunner
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

object MobiusFactory {

    data class Result<Model, Event, Effect>(
        val mobius: MobiusLoop<Model, Event, Effect>,
        val model: Observable<Model>
    )

    fun <Model, Event, Effect>create(
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