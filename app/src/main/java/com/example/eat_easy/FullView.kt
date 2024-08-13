package com.example.eat_easy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FullView : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_full_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val Image:ImageView=findViewById(R.id.viewimg)
        val name:TextView=findViewById(R.id.viewname)
        val desc:TextView=findViewById(R.id.viewdesc)
        val intent:Intent
        intent=getIntent()
        val rimage:Int=getIntent().getIntExtra("ReceipeImg",0)
        val rname:String= getIntent().getStringExtra("ReceipeName").toString()
        val rdesc:String= getIntent().getStringExtra("ReceipeName").toString()
        Image.setImageResource(rimage)
        name.text=rname
        desc.text=rdesc

    }
}