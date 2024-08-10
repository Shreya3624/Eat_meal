package com.example.eat_easy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView


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

        val item = items[position]
        imageView.setImageResource(item.titleimage)
        textView.text = item.titlename

        return view
    }
}