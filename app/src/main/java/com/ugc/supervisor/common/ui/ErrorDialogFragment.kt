package com.ugc.supervisor.common.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.ugc.supervisor.R
import kotlinx.android.synthetic.main.simple_dialog_fragment.*

class ErrorDialogFragment : DialogFragment() {

    var isLargeSize = false


    private var title: String? = null
    private var message: String? = null

    fun setTitle(title: String?): ErrorDialogFragment {
        this.title = title
        return this
    }

    fun setMessage(message: String?): ErrorDialogFragment {
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
            inflater.inflate(R.layout.simple_dialog_fragment, parent, false)

        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }


        return thisView
    }

    override fun onResume() {
        super.onResume()

        cancel_button.setOnClickListener({ l -> dismiss()})

        if (!TextUtils.isEmpty(title)) {
            title_tv.text = title
        }
        if (!TextUtils.isEmpty(message)) {
            message_tv.text = message
        }
    }

    private fun pxToDp(px: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            px.toFloat(),
            resources.displayMetrics
        ).toInt()
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