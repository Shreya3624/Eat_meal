package com.example.eat_easy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class sign_up : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val txt_uname:EditText=findViewById(R.id.txt_uname)
        val txt_mail:EditText=findViewById(R.id.txt_mail)
        val txt_pass:EditText=findViewById(R.id.txt_pass)
        val txt_weight:EditText=findViewById(R.id.txt_weight)
        val txt_height:EditText=findViewById(R.id.txt_height)
        val txt_sign:TextView = findViewById(R.id.txt_sign)
        val txt1:TextView = findViewById(R.id.txt1)
        txt1.setOnClickListener {
            val intent1 = Intent(this,login::class.java)
            startActivity(intent1)
        }
        val btn:Button=findViewById(R.id.btn)
        btn.setOnClickListener {
            if (txt_uname.text.toString().isEmpty()) {
                txt_uname.setError("Field can't be empty!")
            }
            fun isValidEmail(email: String): Boolean {
                val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
                return email.matches(emailRegex)
            }
            val emailInput = txt_mail.text.toString()

            if (isValidEmail(emailInput)) {
                val intent = Intent(this,home_page::class.java)
                startActivity(intent)
            } else {
                txt_mail.setError("Please enter valid Email")
            }
            if(txt_pass.length()<6){
                txt_pass.setError("Maximum 6 character required")
            }
            if (txt_weight.text.toString().isEmpty()) {
                txt_weight.setError("Field can't be empty!")
            }
            if (txt_height.text.toString().isEmpty()) {
                txt_height.setError("Field can't be empty!")
            }
        }


    }
}

