package com.example.eat_easy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView


class desc_adapter(private val context: Context, private val items:List<recipe_list_model>) : BaseAdapter() {
    override fun getCount(): Int =items.size


    override fun getItem(position: Int): Any =items[position]

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.desc_adapter, parent, false)
        val imageView: ImageView = view.findViewById(R.id.desc_img)
        val textView: TextView = view.findViewById(R.id.desc_text)
        val adcradview:CardView=view.findViewById(R.id.adcradview)
        val item = items[position]
        val rname:String=item.titlename
        val rimage:Int=item.titleimage
        val rdesc:String=item.titledesc
        imageView.setImageResource(rimage)
        textView.text = rname
        //detailed receipe
        adcradview.setOnClickListener {

            val intent=Intent(context,FullView::class.java)
            intent.putExtra("ReceipeImg",rimage)
            intent.putExtra("ReceipeName",rname)
            intent.putExtra("ReceipeDesc",rdesc)
            context.startActivity(intent)
        }

        return view
    }
}