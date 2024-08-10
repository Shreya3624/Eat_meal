package com.example.eat_easy

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class home : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=  inflater.inflate(R.layout.fragment_home, container, false)
       val button: Button = view.findViewById(R.id.btn_1)
       button.setOnClickListener {
           val intent = Intent(activity,recipelist::class.java)
           startActivity(intent);
       }
// Inflate the layout for this fragment

        return view
    }
}