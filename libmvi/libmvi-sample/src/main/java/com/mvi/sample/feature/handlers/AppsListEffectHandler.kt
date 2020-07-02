package com.mvi.sample.feature.handlers

import com.mvi.api.MviEffectHandler
import com.mvi.sample.feature.AppsListAction
import com.mvi.sample.feature.AppsListEffect

interface AppsListEffectHandler : MviEffectHandler<AppsListAction, AppsListEffect>