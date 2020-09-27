package com.example.geopagos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geopagos.R
import com.facebook.stetho.Stetho

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Stetho.initializeWithDefaults(this)

    }

}