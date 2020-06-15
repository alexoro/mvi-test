package com.mvi.mobius

import com.mvi.api.MviActionExecutor

interface SampleActionExecutor : MviActionExecutor<SampleState, SampleAction1, SampleEffect1> {

    override fun apply(
        state: SampleState,
        action: SampleAction1
    ): MviActionExecutor.Result<SampleState, SampleEffect1> {
        return MviActionExecutor.Result()
    }

}