package com.mvi.sample

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import com.mvi.sample.feature.AppsListFeature
import com.mvi.sample.view.AppsListView

class SampleActivity : Activity() {

    private lateinit var feature: AppsListFeature
    private lateinit var view: AppsListView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sample_main_activity)

        feature = AppsListFeature()
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