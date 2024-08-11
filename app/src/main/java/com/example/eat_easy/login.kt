package com.example.eat_easy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btn:Button = findViewById(R.id.btn)
        btn.setOnClickListener{
            val intent2 =Intent(this,home_page::class.java)
            startActivity(intent2)
        }
        val text2:TextView=findViewById(R.id.text2)
        text2.setOnClickListener{
            val intent3 =Intent(this,sign_up::class.java)
            startActivity(intent3)
        }
    }
}