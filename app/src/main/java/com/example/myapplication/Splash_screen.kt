package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait

class Splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val i= Intent(this,home_activity::class.java)
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                waits()
            }
            withContext(Dispatchers.Main){
                startActivity(i)
            }
        }
    }
}
fun waits(){
    var a=System.currentTimeMillis()
    while (a+2200>System.currentTimeMillis()){

    }
}