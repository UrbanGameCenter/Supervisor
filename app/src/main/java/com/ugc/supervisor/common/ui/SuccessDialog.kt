package com.ugc.supervisor.common.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ugc.supervisor.R
import kotlinx.android.synthetic.main.success_dialog.*

class SuccessDialog(context: Context) :
    Dialog(context, android.R.style.Theme_Translucent_NoTitleBar) {

    lateinit var message : String

    fun setText(text: String): SuccessDialog {
        message = text
        return this
    }

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.success_dialog)
        success_text.setText(message)
        root_view.setOnClickListener { v: View? -> dismiss() }
        setCanceledOnTouchOutside(true)
    }

    init {
        setCancelable(true)
    }
}