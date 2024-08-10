package com.example.eat_easy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView

class recipelist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipelist)
        val gvview:GridView = findViewById(R.id.gv_desc)
        val items = listOf(
            recipe_list_model(R.drawable.app_logo, "Item 1"),
            recipe_list_model(R.drawable.indian, "Item 2"),
            recipe_list_model(R.drawable.italian, "Item 3"),
            recipe_list_model(R.drawable.maxican, "Item 4"),
            recipe_list_model(R.drawable.app_logo, "Item 5"),
            recipe_list_model(R.drawable.indian, "Item 6"),
            recipe_list_model(R.drawable.italian, "Item 7"),
            recipe_list_model(R.drawable.maxican, "Item 8")
        )
        val adapter = desc_adapter(this, items)
        gvview.adapter = adapter
    }
}