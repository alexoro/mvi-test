package com.mvi.mobius_simple.view

import android.content.DialogInterface
import android.view.ViewStub
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.mvi.R
import com.mvi.mobius_simple.base.MviView
import com.mvi.mobius_simple.feature.AppsListEvents
import com.mvi.mobius_simple.feature.AppsListFeature
import io.reactivex.disposables.Disposable

class AppsListView(
    private val feature: AppsListFeature,
    stubContainer: ViewStub
) : MviView {

    private val context = stubContainer.context
    private val view = stubContainer.let {
        it.layoutResource = R.layout.main_content
        it.inflate()
    }
    private val loadInitView = view.findViewById<Button>(R.id.load_init)
    private val sendLogsView = view.findViewById<Button>(R.id.send_logs)
    private val resultView = view.findViewById<TextView>(R.id.result)
    private val observeDisposable: Disposable

    private var sendLogsDialog: DialogInterface? = null

    init {
        loadInitView.setOnClickListener {
            feature.submit(AppsListEvents.InitLoad.OnStart)
        }
        sendLogsView.setOnClickListener {
            feature.submit(AppsListEvents.SendLogs.OnStart("Hello world!"))
        }
        observeDisposable = feature.observeModel().subscribe {
            resultView.text = "Loading = ${it.isLoading}, List = ${it.list}"
            when (it.sendingLogs) {
                true -> showSendingLogsDialog()
                false -> hideSendingLogsDialog()
            }
        }
    }

    override fun destroy() {
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
                    feature.submit(AppsListEvents.SendLogs.OnCancel)
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