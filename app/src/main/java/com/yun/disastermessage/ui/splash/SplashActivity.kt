package com.yun.disastermessage.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.yun.disastermessage.R
import com.yun.disastermessage.data.Constant.INTERNET_ERROR
import com.yun.disastermessage.data.Constant.REMOTE_KEY_APP_VERSION
import com.yun.disastermessage.data.Constant.TAG
import com.yun.disastermessage.data.Constant.UPDATE_ERROR
import com.yun.disastermessage.ui.main.MainActivity
import com.yun.disastermessage.ui.popup.OneButtonPopup

class SplashActivity : AppCompatActivity() {

    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings { minimumFetchIntervalInSeconds = 0 }
        remoteConfig.setConfigSettingsAsync(configSettings)

    }

    override fun onBackPressed() {

    }

    private fun fetchAppVersion() {
        val appVersion = remoteConfig[REMOTE_KEY_APP_VERSION].asString()

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val serverAppVersion = appVersion.split('.')
                    val deviceAppVersion =
                        packageManager.getPackageInfo(packageName, 0).versionName.split('.')
                    Log.d(TAG, "통과 $serverAppVersion : $deviceAppVersion")
                    if (appVersion == "") {
                        // 서버에서 값 못가져옴
                        fetchAppVersion()
                        Log.d(TAG, "if -> 서버에서 값 못가져옴")
                    } else if (serverAppVersion[0] != deviceAppVersion[0]
                        || serverAppVersion[1] != deviceAppVersion[1]
                    ) {
                        // 메이저 및 마이너 업데이트
                        Log.d(TAG, "else if -> 업데이트 해야 함")
                        showWarningPopup(
                            this.getString(R.string.update_title),
                            this.getString(R.string.update_contents),
                            UPDATE_ERROR
                        )
                    } else {

                        Handler().postDelayed({
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }, 1000)
                    }
                } else {
                    Log.d(TAG, it.exception.toString())
                    showWarningPopup(
                        this.getString(R.string.error_title),
                        this.getString(R.string.internet_contents),
                        INTERNET_ERROR
                    )
                }
            }
    }

    override fun onResume() {
        super.onResume()
        fetchAppVersion()
    }

    private fun showWarningPopup(title: String, contents: String, flag: String) {
        OneButtonPopup().apply {
            showPopup(
                this@SplashActivity,
                title,
                contents
            )
            setDialogListener(object : OneButtonPopup.CustomDialogListener {
                override fun onResultClicked(result: Boolean) {
                    if (result) {
                        when (flag) {
                            INTERNET_ERROR -> {
                                finish()
                            }
                            UPDATE_ERROR -> {
//                                intent.data =
//                                    Uri.parse(this@SplashActivity.getString(R.string.store_uri))
//                                this@SplashActivity.startActivity(intent)
                                Toast.makeText(applicationContext, "스토어로 이동", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            })
        }
    }
}