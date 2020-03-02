package com.ugc.supervisor.splashscreen

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.ugc.supervisor.R
import com.ugc.supervisor.core.AbstractActivity
import com.ugc.supervisor.core.RequestCallBack
import com.ugc.supervisor.model.Config
import com.ugc.supervisor.model.UgcError
import com.ugc.supervisor.services.TechnicalService
import com.ugc.supervisor.supervisor.SupervisorActivity
import kotlinx.android.synthetic.main.splashscreen_activity.*

class SplashScreenActivity : AbstractActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splashscreen_activity)
        setTranslucideStatusBar()

        showAppVersion()

        retry_button.setOnClickListener { showRetry() }
    }

    override fun onResume() {
        super.onResume()

            getConfig()
    }

    private fun getConfig() {

        showLoader()

        TechnicalService(baseContext).getConfig(
            object : RequestCallBack<Config> {

                override fun onSuccess(response: Config) {
                    Log.d("test" , response.status)
                    startActivity(SupervisorActivity.newIntent(baseContext))
                    finishAffinity()
                }

                override fun onError(error: UgcError) {
                    showError(error.message)
                    showRetry()
                }
            }
        )
    }

    private fun showLoader() {
        progress_wheel.visibility = View.VISIBLE
        retry_button.visibility = View.GONE
        val rotation = AnimationUtils.loadAnimation(baseContext, R.anim.rotate)
        rotation.repeatCount = Animation.INFINITE

        if (progress_wheel != null) {
            progress_wheel.startAnimation(rotation)
        }
    }

    private fun showRetry(){
        progress_wheel.visibility = View.GONE
        retry_button.visibility = View.VISIBLE
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
