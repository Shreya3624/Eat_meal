package com.example.eat_easy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView


class customlistview(context: Context, private val items: ArrayList<String>,private val Tempid:ArrayList<Int>) :
    ArrayAdapter<String>(context, 0, items) {
    private lateinit var dbHelper: DataBase
    private val selectedPositions = mutableSetOf<Int>() // To track selected positions

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.customlistview  , parent, false)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val textViewItemName = view.findViewById<TextView>(R.id.textViewItemName)
        val buttonDelete = view.findViewById<TextView>(R.id.buttonDelete)

        textViewItemName.text = items[position]
        dbHelper=DataBase(context)
if (!checkBox.isChecked){
    buttonDelete.visibility=View.INVISIBLE
}
        // Handle checkbox check/uncheck
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            buttonDelete.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        // Handle delete button click
        buttonDelete.setOnClickListener {
            val groceryItemId = Tempid[position]
            val deleted = dbHelper.deleteGroceryItem(groceryItemId)
            if (deleted) {
                // Remove the item from the list
                items.removeAt(position)
                Tempid.removeAt(position)  // Also remove the ID from the list
                checkBox.isChecked=false
                notifyDataSetChanged()
            }
        }

        return view
    }
}