package com.example.eat_easy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class sign_up : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val txt_uname:EditText=findViewById(R.id.txt_uname)
        val txt_mail:EditText=findViewById(R.id.txt_mail)
        val txt_pass:EditText=findViewById(R.id.txt_pass)
        val txt_weight:EditText=findViewById(R.id.txt_weight)
        val txt_height:EditText=findViewById(R.id.txt_height)
        val txt1:TextView = findViewById(R.id.txt1)
        val txt_bmi:TextView = findViewById(R.id.txt_bmi)
        val btn:Button=findViewById(R.id.btn)
        var weight:Double
        var height:Double
        val emailInput = txt_mail.text.toString()

        txt1.setOnClickListener {
            val intent1 = Intent(this,login::class.java)
            startActivity(intent1)
        }
        btn.setOnClickListener {
            if (txt_uname.text.toString().isEmpty()) {
                txt_uname.setError("Field can't be empty!")
            }
//              if (!isValidEmail(emailInput)) {
//              //  txt_mail.setError("Please enter valid Email")
//            }
           else if(txt_pass.length()<6){
                txt_pass.setError("Maximum 6 character required")
            }
          else  if (txt_weight.text.toString().isEmpty()) {
                txt_weight.setError("Field can't be empty!")
              //  val weight = .toDoubleOrNull()
            }
           else if (txt_height.text.toString().isEmpty()) {
                txt_height.setError("Field can't be empty!")
            }

            else{
               weight=(txt_weight.text.toString()).toDouble()
                height=(txt_height.text.toString()).toDouble()
                val bmi = calculateBMI(weight, height)

                when {
                    bmi < 18.5 -> Toast.makeText(this,"$bmi underweight",Toast.LENGTH_LONG).show()
                    bmi in 18.5..24.9 -> Toast.makeText(this,"$bmi normal",Toast.LENGTH_LONG).show()
                    bmi in 25.0..29.9 -> Toast.makeText(this,"$bmi overweight",Toast.LENGTH_LONG).show()
                    else ->  Toast.makeText(this,"can't calculate",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex)
    }
    fun calculateBMI(weight:Double,height:Double):Double {
        return weight / (height * height)
    }
}

