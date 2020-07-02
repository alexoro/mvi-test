package com.mvi.sample.feature.handlers

import com.mvi.api.MviActionHandler
import com.mvi.sample.feature.AppsListAction
import com.mvi.sample.feature.AppsListEffect
import com.mvi.sample.feature.AppsListState

interface AppsListActionHandler : MviActionHandler<AppsListState, AppsListAction, AppsListEffect>