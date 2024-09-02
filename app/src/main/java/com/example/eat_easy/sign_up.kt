package com.example.eat_easy

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class sign_up : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etWeight: EditText
    private lateinit var etHeight: EditText
    private lateinit var btnSignup: Button
    private lateinit var dbHelper: DataBase
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        dbHelper = DataBase(this)
         etUsername=findViewById(R.id.txt_uname)
         etEmail=findViewById(R.id.txt_mail)
         etPassword=findViewById(R.id.txt_pass)
         etWeight=findViewById(R.id.txt_weight)
         etHeight=findViewById(R.id.txt_height)
        val txt1:TextView = findViewById(R.id.txt1)
        val txt_bmi:TextView = findViewById(R.id.txt_bmi)
         btnSignup=findViewById(R.id.btn)
        txt1.setOnClickListener {
            val intent1 = Intent(this,login::class.java)
            startActivity(intent1)
            finish()
        }
        btnSignup.setOnClickListener {
                if (validateInputs()) {
                    val email = etEmail.text.toString().trim()

                    // Now we check if the email is unique
                    if (isEmailUnique(email)) {
                        val username = etUsername.text.toString().trim()
                        val password = etPassword.text.toString().trim()
                        val weight = etWeight.text.toString().toInt()
                        val height = etHeight.text.toString().toInt()

                        val bmi = calculateBMI(weight, height)
                        dbHelper.addUser(username, email, password, weight, height, bmi)
                        Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()

                        // Move to the next activity (MainActivity)
                        val intent = Intent(this, login::class.java)
                        startActivity(intent)
                        finish() // Close SignupActivity
                    } else {
                        etEmail.error = "Email is already in use"
                        etEmail.requestFocus()
                    }
                }
            else
                Toast.makeText(this, "Signup Fail!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isEmailUnique(email: String): Boolean {
        // Check if the email is empty
        if (email.isEmpty()) {
            return false // Treat empty email as non-unique for safety
        }

        val cursor: Cursor? = dbHelper.getUserByEmail(email)
        val isUnique = cursor?.count == 0
        cursor?.close()
        return isUnique
    }



    private fun validateInputs(): Boolean {
        val username = etUsername.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val weight = etWeight.text.toString().trim()
        val height = etHeight.text.toString().trim()

        if (username.isEmpty()) {
            etUsername.error = "Username is required"
            etUsername.requestFocus()
            return false
        }

        if (email.isEmpty() || !isValidEmail(email)) {
            etEmail.error = "Valid email is required"
            etEmail.requestFocus()
            return false
        }

        if (password.isEmpty() || password.length < 6) {
            etPassword.error = "Password must be at least 6 characters"
            etPassword.requestFocus()
            return false
        }

        if (weight.isEmpty() || weight.toIntOrNull() == null) {
            etWeight.error = "Valid weight is required"
            etWeight.requestFocus()
            return false
        }

        if (height.isEmpty() || height.toIntOrNull() == null) {
            etHeight.error = "Valid height is required"
            etHeight.requestFocus()
            return false
        }

        return true
    }

    private fun calculateBMI(weight: Int, height: Int): String {
        val heightInMeters = height / 100.0
        val bmi = weight / (heightInMeters * heightInMeters)
        return String.format("%.2f", bmi)
    }
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex)
    }

}

