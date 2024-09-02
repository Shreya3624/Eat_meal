package com.example.eat_easy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class home : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=  inflater.inflate(R.layout.fragment_home, container, false)
       val btnind: Button = view.findViewById(R.id.btn_inidan)
        val btnita: Button = view.findViewById(R.id.btn_italian)
        val btnmax: Button = view.findViewById(R.id.btn_maxican)
        btnind.setOnClickListener {
           val intent = Intent(activity,recipelist::class.java)
           intent.putExtra("condition",1)
           startActivity(intent)
       }
        btnita.setOnClickListener {
            val intent = Intent(activity,recipelist::class.java)
            intent.putExtra("condition",2)
            startActivity(intent)
        }
        btnmax.setOnClickListener {
            val intent = Intent(activity,recipelist::class.java)
            intent.putExtra("condition",3)
            startActivity(intent)
        }
        return view
    }
}