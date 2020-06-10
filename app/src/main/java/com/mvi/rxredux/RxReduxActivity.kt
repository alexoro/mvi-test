package com.mvi.rxredux

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import com.mvi.R
import com.mvi.imengine.ImEngine
import com.mvi.rxredux.feature.AppsListFeature
import com.mvi.rxredux.view.AppsListView

class RxReduxActivity : Activity() {

    private lateinit var feature: AppsListFeature
    private lateinit var view: AppsListView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        feature = AppsListFeature(
            imEngine = ImEngine())
        view = AppsListView(
            feature = feature,
            stubContainer = findViewById(R.id.container))
    }

    override fun onDestroy() {
        super.onDestroy()
        view.destroy()
        feature.destroy()
    }

}