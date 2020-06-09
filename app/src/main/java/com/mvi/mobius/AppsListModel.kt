package com.mvi.mobius

data class AppsListModel(
    val isLoading: Boolean = false,
    val list: List<String> = emptyList(),
    val initLoadError: Throwable? = null
)