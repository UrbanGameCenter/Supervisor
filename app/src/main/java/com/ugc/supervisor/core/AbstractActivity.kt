package com.ugc.supervisor.core

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ugc.supervisor.common.ui.ErrorDialogFragment
import com.ugc.supervisor.common.ui.ProgressDialog
import com.ugc.supervisor.common.ui.SuccessDialog

abstract class AbstractActivity : AppCompatActivity() {

    val ERROR_DIALOG = "errorDialog"

    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeProgressDialog()
    }

    open fun setTranslucideStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    open fun setFullscreenMode() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT
    }

    open fun setDarkStatusIcon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        }
    }

    protected open fun makeProgressDialog() {
        progressDialog = ProgressDialog(this)
    }

    open fun showProgressDialog() {
        progressDialog.setText("")
        progressDialog.show()
    }

    open fun showProgressDialog(message: String) {
        progressDialog.setText(message)
        progressDialog.show()
    }

    open fun hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing() && !this.isDestroyed) {
            progressDialog.dismiss()
        }
    }

    open fun showSuccessDialog(message: String) {
        SuccessDialog(this)
            .setText(message)
            .show()
    }

    open fun showError(message: String?) {
        showError(null, message)
    }

    open fun showError(title: String?, message: String?) {
        val errorDialogFragment: ErrorDialogFragment = ErrorDialogFragment()
            .setTitle(title)
            .setMessage(message)
        errorDialogFragment.show(supportFragmentManager, ERROR_DIALOG)
    }
}