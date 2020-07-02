package com.mvi.sample.feature

import com.mvi.api.MviActionHandlerComposer
import com.mvi.api.MviEffectHandlerComposer
import com.mvi.impl.mobius.MobiusMviEngine
import com.mvi.sample.feature.handlers.AppsListLogger
import com.mvi.sample.feature.handlers.actions.*
import com.mvi.sample.feature.handlers.effects.AppsListInitLoadEffectHandler
import com.mvi.sample.feature.handlers.effects.AppsListSendLogsEffectHandler
import io.reactivex.Observable

@Suppress("JoinDeclarationAndAssignment")
class AppsListFeature {

    private val engine = MobiusMviEngine(
        initialState = AppsListState(),
        actionHandler = MviActionHandlerComposer(
            AppsListInitLoadStartActionHandler(),
            AppsListInitLoadOnSuccessActionHandler(),
            AppsListInitLoadOnErrorActionHandler(),
            AppsListSendLogsStartActionHandler(),
            AppsListSendLogsCancelActionHandler(),
            AppsListSendLogsOnCompleteActionHandler(),
            AppsListSendLogsOnErrorActionHandler()
        ),
        effectHandler = MviEffectHandlerComposer(
            AppsListInitLoadEffectHandler(),
            AppsListSendLogsEffectHandler()
        ),
        logger = AppsListLogger()
    )

    fun submit(action: AppsListAction) {
        engine.submit(action)
    }

    fun getState(): AppsListState {
        return engine.getState()
    }

    fun observeState(): Observable<AppsListState> {
        return engine.observeState()
    }

    fun destroy() {
        engine.shutdown()
    }

}