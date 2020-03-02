package com.ugc.supervisor.splashscreen

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.WorkerThread
import com.ugc.supervisor.R
import com.ugc.supervisor.core.AbstractActivity
import com.ugc.supervisor.core.RequestCallBack
import com.ugc.supervisor.model.Config
import com.ugc.supervisor.model.DefaultResponse
import com.ugc.supervisor.model.UgcError
import com.ugc.supervisor.services.TechnicalService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SplashScreenActivity : AbstractActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splashscreen_activity)
        setTranslucideStatusBar()

        showAppVersion()
    }

    override fun onResume() {
        super.onResume()

            showProgressDialog()
            getConfig()

    }

    private fun getConfig() {
        TechnicalService(baseContext).getConfig(
            object : RequestCallBack<Config> {

                override fun onSuccess(response: Config) {
                    showSuccessDialog("yeaaah")
                    Log.d("test" , response.status)
                }

                override fun onError(error: UgcError) {
                    showError(error.message)
                }
            }
        )
    }

    private fun showAppVersion() {
        try {
            val version =
                packageManager.getPackageInfo(packageName, 0).versionName
            val versionText: String = getString(R.string.version_string).plus(version)
            findViewById<TextView>(R.id.app_version_textview).setText(versionText)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }
}
