package com.example.geopagos.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.geopagos.R
import com.example.geopagos.viewmodel.PaymentMethodViewModel
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Stetho.initializeWithDefaults(this)



    }

}