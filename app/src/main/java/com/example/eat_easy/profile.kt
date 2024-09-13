package com.example.eat_easy

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.eat_easy.DataBase.Companion.COLUMN_BMI
import com.example.eat_easy.DataBase.Companion.COLUMN_USERNAME


class profile : Fragment() {
    private lateinit var USERNAME: TextView
    private lateinit var Email: TextView
    private lateinit var Bmi: TextView
    private lateinit var btn_logOut: Button
    private lateinit var dbHelper: DataBase
    private lateinit var shared:Shareprefrence

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        USERNAME = view.findViewById(R.id.txt_uname)
        Email = view.findViewById(R.id.pr_email)
        Bmi = view.findViewById(R.id.pr_bmi)
        btn_logOut = view.findViewById(R.id.btn)
        dbHelper = DataBase(requireContext())
        shared= Shareprefrence(requireContext())
        val email = shared.getEmail()
        // Assuming the email is passed as an argument
        if (email != null) {
            loadUserData(email)
        } else {
            Toast.makeText(requireContext(), "No user data found", Toast.LENGTH_SHORT).show()
        }
        btn_logOut.setOnClickListener {
            shared.setlogin(false)
         //   shared.logout()
            val intent = Intent(requireContext(), login::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        return view
}
    private fun loadUserData(email: String) {
        val cursor: Cursor? = dbHelper.getUserByEmail(email)
        if (cursor != null && cursor.moveToFirst()) {
            val username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
            val bmi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BMI))

            // Set the data to the UI elements
            USERNAME.text = username
            Email.text=email
            Bmi.text = "My BMI: $bmi"

            cursor.close()
        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
    }
}