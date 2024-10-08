package com.example.eat_easy

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import de.hdodenhof.circleimageview.CircleImageView

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
        val Image: CircleImageView =findViewById(R.id.viewimg)
        val name:TextView=findViewById(R.id.viewname)
        val desc:TextView=findViewById(R.id.viewdesc)
        val intent:Intent = intent
        val rimage:Int=intent.getIntExtra("ReceipeImg",0)
        val rname:String= intent.getStringExtra("ReceipeName").toString()
        val rdesc:String= intent.getStringExtra("ReceipeDesc").toString()
        Image.setImageResource(rimage)
        name.text=rname
        desc.text=rdesc

    }
}