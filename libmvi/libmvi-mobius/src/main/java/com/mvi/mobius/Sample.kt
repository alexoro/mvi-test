package com.mvi.mobius

import com.mvi.api.MviActionExecutor

class Sample {

    init {
        val engine = MobiusMviEngine(
            initialState = SampleState(),
//            actions = listOf(
//                SampleAction1::class.java,
//                SampleAction2::class.java
//            ),
            actionsExecutor = listOf(
                SampleAction1Executor(),
                SampleAction2Executor()
            )
        )


    }

}