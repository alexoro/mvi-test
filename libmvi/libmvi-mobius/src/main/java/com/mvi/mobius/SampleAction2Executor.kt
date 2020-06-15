package com.mvi.mobius

import com.mvi.api.MviAction
import com.mvi.api.MviActionExecutor

class SampleAction2Executor : MviActionExecutor<SampleState> {

    override fun apply(state: SampleState, action: MviAction): MviActionExecutor.Result<SampleState> {
        return MviActionExecutor.Result()
    }

}