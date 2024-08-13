package com.example.eat_easy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView

class recipelist : AppCompatActivity() {
    lateinit var items:List<recipe_list_model>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipelist)
        val intent:Intent
        intent=getIntent()
        val condition:Int=intent.getIntExtra("condition",0)
        val gvview:GridView = findViewById(R.id.gv_desc)

       if (condition==1){
           indian()
       }
        else if(condition==2){
            italian()
        }
        else if(condition==3){
            maxican()
        }
        val adapter = desc_adapter(this, items)
        gvview.adapter = adapter
    }

    private fun maxican() {
        items = listOf(
            recipe_list_model(R.drawable.paneer_makhani, " Paneer Makhani "),
            recipe_list_model(R.drawable.chole, "shreya"),
            recipe_list_model(R.drawable.bhindi_masala, "ankoit Bhindi"),
            recipe_list_model(R.drawable.biryani, "manoj. Biryani"),
            recipe_list_model(R.drawable.aaloo_sabji, "Jeera Aloo"),
            recipe_list_model(R.drawable.dal, "Dal vishal"),
        )


    }

    private fun indian(){
        items = listOf(
            recipe_list_model(R.drawable.paneer_makhani, " Paneer Makhani "),
            recipe_list_model(R.drawable.chole, "Chole"),
            recipe_list_model(R.drawable.bhindi_masala, "Masala Bhindi"),
            recipe_list_model(R.drawable.biryani, "Veg. Biryani"),
            recipe_list_model(R.drawable.aaloo_sabji, "Jeera Aloo"),
            recipe_list_model(R.drawable.dal, "Dal Tadka"),
        )
    }

    private fun italian() {
        items = listOf(
            recipe_list_model(R.drawable.burger, " Cheese Burger "),
            recipe_list_model(R.drawable.lasagna1, "Lasagania"),
            recipe_list_model(R.drawable.noodles, "Tomato Spegheti"),
            recipe_list_model(R.drawable.pizza, "Cheese Burst Pizza"),
            recipe_list_model(R.drawable.spegheti, "Spegheti"),
            recipe_list_model(R.drawable.pasta_salad, "Pasata Salad"),
        )

    }


}