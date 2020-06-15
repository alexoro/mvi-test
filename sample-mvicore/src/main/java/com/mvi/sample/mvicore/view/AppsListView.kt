package com.mvi.sample.mvicore.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.ViewStub
import android.widget.Button
import android.widget.TextView
import com.mvi.sample.mvicore.R
import com.mvi.sample.mvicore.feature.AppsListFeature
import com.mvi.sample.mvicore.feature.AppsListWishes
import io.reactivex.disposables.Disposable

class AppsListView(
    private val feature: AppsListFeature,
    stubContainer: ViewStub
) {

    private val context = stubContainer.context
    private val view = stubContainer.let {
        it.layoutResource = R.layout.mvicore_main_content
        it.inflate()
    }
    private val loadInitView = view.findViewById<Button>(R.id.load_init)
    private val sendLogsView = view.findViewById<Button>(R.id.send_logs)
    private val resultView = view.findViewById<TextView>(R.id.result)
    private val observeDisposable: Disposable

    private var sendLogsDialog: DialogInterface? = null

    init {
        loadInitView.setOnClickListener {
            feature.submit(AppsListWishes.InitLoad.Start)
        }
        sendLogsView.setOnClickListener {
            feature.submit(AppsListWishes.SendLogs.Start("Hello world!"))
        }
        observeDisposable = feature.observeState().subscribe {
            resultView.text = "Loading = ${it.isLoading}, List = ${it.list}"
            when (it.sendingLogs) {
                true -> showSendingLogsDialog()
                false -> hideSendingLogsDialog()
            }
        }
    }

    fun destroy() {
        observeDisposable.dispose()
        hideSendingLogsDialog()
    }

    private fun showSendingLogsDialog() {
        if (sendLogsDialog == null) {
            sendLogsDialog = AlertDialog.Builder(context)
                .setTitle("Sending logs!")
                .setMessage("Still in progress!")
                .setCancelable(false)
                .setNeutralButton("Cancel") { _, _ ->
                    feature.submit(AppsListWishes.SendLogs.Cancel)
                }
                .setOnDismissListener {
                    sendLogsDialog = null
                }
                .show()
        }
    }

    private fun hideSendingLogsDialog() {
        sendLogsDialog?.dismiss()
        sendLogsDialog = null
    }

}