package com.example.eat_easy

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.concurrent.DelayQueue

class login : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var dbHelper: DataBase
    private lateinit var shared:Shareprefrence
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etEmail = findViewById(R.id.txt_uname)
        etPassword = findViewById(R.id.txt_pass)
        btnLogin = findViewById(R.id.btn)
        val singup:TextView=findViewById(R.id.text2)
        shared = Shareprefrence(this)
        dbHelper = DataBase(this)
        singup.setOnClickListener{
         //   Navigate to the singup activity
            val intent=Intent(this, sign_up::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener {
            if (validateInputs()) {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (isValidUser(email, password)) {
                    // Successful login
                    shared.storeEmail(email)
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                    shared.setlogin(true)

                    // Navigate to the next activity
                    val intent = Intent(this, home_page::class.java)
                    intent.putExtra("email",email)
                    startActivity(intent)
                    finish() // Close LoginActivity
                } else {
                    // Invalid login credentials
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty()) {
            etEmail.error = "Email is required"
            etEmail.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Valid email is required"
            etEmail.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            etPassword.error = "Password is required"
            etPassword.requestFocus()
            return false
        }

        return true
    }

    private fun isValidUser(email: String, password: String): Boolean {
        val cursor = dbHelper.getUserByEmailAndPassword(email, password)
        val isValid = cursor != null && cursor.count > 0
        cursor?.close()
        return isValid
    }
}