package com.example.eat_easy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btn:Button = findViewById(R.id.btn)
        btn.setOnClickListener{
            val intent2 =Intent(this,home_page::class.java)
            startActivity(intent2)
        }
    }
}