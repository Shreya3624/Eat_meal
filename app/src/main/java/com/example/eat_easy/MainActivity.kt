package com.example.eat_easy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    private lateinit var share:Shareprefrence
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        share= Shareprefrence(this)
        handler= Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (share.getLogin()){
                val intent =Intent(this,home_page::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val intent =Intent(this,sign_up::class.java)
                startActivity(intent)
                finish()
            }
        },2000)
    }
}