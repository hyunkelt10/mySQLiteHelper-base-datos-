package com.example.mysqlite

import android.app.Activity
import android.widget.Toast

fun Activity.toast(text:String, lenght:Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, lenght).show()
}

