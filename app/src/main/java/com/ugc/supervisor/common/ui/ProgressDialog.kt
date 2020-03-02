package com.ugc.supervisor.common.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.ugc.supervisor.R
import kotlinx.android.synthetic.main.progress_dialog.*

class ProgressDialog(context: Context) : Dialog(context, android.R.style.Theme_Translucent_NoTitleBar) {


        lateinit var message: String


        init {
            setCancelable(true)
        }

        fun setText(text: String): ProgressDialog {
            message = text
            return this
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.progress_dialog)
        }

        fun setAnimation() {
            val rotation = AnimationUtils.loadAnimation(context, R.anim.rotate)
            rotation.repeatCount = Animation.INFINITE
            progress_wheel.startAnimation(rotation)
        }

        override fun show() {
            super.show()
            if (TextUtils.isEmpty(message)) {
                loader_text.visibility = View.INVISIBLE
            } else {
                loader_text.visibility = View.VISIBLE
                loader_text.text = message
            }
            setAnimation()
        }

        override fun dismiss() {
            super.dismiss()
        }
    }
