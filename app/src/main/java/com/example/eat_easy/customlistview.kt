package com.example.eat_easy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView


class customlistview(context: Context, private val items: ArrayList<String>) :
    ArrayAdapter<String>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.customlistview  , parent, false)

        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val textViewItemName = view.findViewById<TextView>(R.id.textViewItemName)
        val buttonDelete = view.findViewById<Button>(R.id.buttonDelete)
        textViewItemName.text = items[position]
if (!checkBox.isChecked){
    buttonDelete.visibility=View.INVISIBLE
}
        // Handle checkbox check/uncheck
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            buttonDelete.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        // Handle delete button click
        buttonDelete.setOnClickListener {

            items.removeAt(position)
           checkBox.isChecked=false
            notifyDataSetChanged()
        }

        return view
    }
}
