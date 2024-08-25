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
            recipe_list_model(R.drawable.paneer_makhani ," aryan Masala ",""),
            recipe_list_model(R.drawable.chole, "shreya",""),
            recipe_list_model(R.drawable.bhindi_masala, "ankoit Bhindi",""),
            recipe_list_model(R.drawable.biryani, "manoj. Biryani",""),
            recipe_list_model(R.drawable.aaloo_sabji, "Jeera Aloo",""),
            recipe_list_model(R.drawable.dal, "Dal vishal",""),
        )


    }

    private fun indian(){
        items = listOf(
            recipe_list_model(R.drawable.paneer_makhani, " Paneer Butter Masala ",resources.getString(R.string.f2)),
            recipe_list_model(R.drawable.chole, "Chole",resources.getString(R.string.f3)),
            recipe_list_model(R.drawable.bhindi_masala, "Masala Bhindi",resources.getString(R.string.f4)),
            recipe_list_model(R.drawable.biryani, "Veg. Biryani",resources.getString(R.string.f5)),
            recipe_list_model(R.drawable.aaloo_sabji, "Jeera Aloo",resources.getString(R.string.f6)),
            recipe_list_model(R.drawable.dal, "Dal Tadka",resources.getString(R.string.f7)),
        )
    }

    private fun italian() {
        items = listOf(
            recipe_list_model(R.drawable.lasagna1, " Lasagnia",resources.getString(R.string.i1)),
            recipe_list_model(R.drawable.spegheti, "Red Sauce Pasta",resources.getString(R.string.i2)),
            recipe_list_model(R.drawable.burger, "Veg. Cheese Burger",resources.getString(R.string.i3)),
            recipe_list_model(R.drawable.noodles, "Spegheti",""),
            recipe_list_model(R.drawable.pasta_salad, "Pasta Salad",""),
            recipe_list_model(R.drawable.pizza, "Pizza",""),
        )

    }


}