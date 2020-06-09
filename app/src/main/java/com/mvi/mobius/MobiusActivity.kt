package com.mvi.mobius

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.mvi.R
import com.spotify.mobius.Mobius

class MobiusActivity : Activity() {

    private lateinit var loadInitView: Button
    private lateinit var sendLogsView: Button
    private lateinit var resultView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        loadInitView = findViewById(R.id.load_init)
        sendLogsView = findViewById(R.id.send_logs)
        resultView = findViewById(R.id.result)

        val loop = Mobius.loop<AppsListModel, AppsListEvents, AppsListEffects>(
            AppsListLogic(),
            AppsListEffectHandler()
        ).startFrom(AppsListModel())

        loadInitView.setOnClickListener {
            loop.dispatchEvent(AppsListEvents.InitLoad.OnStart)
        }
        sendLogsView.setOnClickListener {
            loop.dispatchEvent(AppsListEvents.SendLogs("Hello world!"))
        }

        loop.observe {
            resultView.text = "Loading = ${it.isLoading}, List = ${it.list}"
        }

    }

}