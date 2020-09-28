package com.example.geopagos.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.geopagos.BuildConfig
import com.example.geopagos.R
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)

        splash.setAnimation("payments.json")
        splash.playAnimation()

        val run = Runnable {
            startActivity(MainActivity.getLaunchIntent(this))
            overridePendingTransition(R.anim.left_in, R.anim.left_out)
        }
        Handler(Looper.getMainLooper()).postDelayed(run,2500)
    }
}