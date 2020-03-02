package com.ugc.supervisor.common.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.ugc.supervisor.R
import kotlinx.android.synthetic.main.progress_dialog.*
import kotlinx.android.synthetic.main.simple_dialog_fragment.*
import kotlinx.android.synthetic.main.success_dialog.*

class SuccessDialog :
    DialogFragment() {

    private var message: String? = null


    fun setMessage(message: String?): SuccessDialog {
        this.message = message
        return this
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        state: Bundle?
    ): View? {
        super.onCreateView(inflater, parent, state)
        val thisView: View =
            inflater.inflate(R.layout.success_dialog, parent, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }

        return thisView
    }

    override fun onResume() {
        super.onResume()

        root_view.setOnClickListener({ l -> dismiss()})

        if (!TextUtils.isEmpty(message)) {
            success_text.text = message
        }
    }

    init {
        setCancelable(true)
    }

    override fun show(
        manager: FragmentManager,
        tag: String?
    ) {
        val fragmentTransaction =
            manager.beginTransaction()
        fragmentTransaction.add(this, tag)
        fragmentTransaction.commitAllowingStateLoss()
    }
}