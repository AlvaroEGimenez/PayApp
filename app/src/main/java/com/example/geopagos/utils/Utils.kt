package com.example.geopagos.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.toastShort(message:String){
    Toast.makeText(this, message , Toast.LENGTH_SHORT).show()
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}