package com.example.eat_easy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class sign_up : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val txt1:TextView = findViewById(R.id.txt1)
        txt1.setOnClickListener {
            val intent1 = Intent(this,login::class.java)
            startActivity(intent1)
        }
    }
}