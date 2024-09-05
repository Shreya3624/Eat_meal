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
    private lateinit var dbHelper: DataBase
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.customlistview  , parent, false)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val textViewItemName = view.findViewById<TextView>(R.id.textViewItemName)
        val buttonDelete = view.findViewById<Button>(R.id.buttonDelete)
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
            dbHelper.deleteGroceryItem(position)
            items.removeAt(position)
           checkBox.isChecked=false
            notifyDataSetChanged()
        }

        return view
    }
}
//package com.example.eat_easy
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ArrayAdapter
//import android.widget.Button
//import android.widget.CheckBox
//import android.widget.TextView
//
//class customlistview(
//    context: Context,
//    private val items: ArrayList<String>,
//    private val groceryItemIds: ArrayList<Int>  // List of item IDs (groceryList_id)
//) : ArrayAdapter<String>(context, 0, items) {
//
//    private lateinit var dbHelper: DataBase
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.customlistview, parent, false)
//        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
//        val textViewItemName = view.findViewById<TextView>(R.id.textViewItemName)
//        val buttonDelete = view.findViewById<Button>(R.id.buttonDelete)
//
//        dbHelper = DataBase(context)
//
//        textViewItemName.text = items[position]
//
//        // Initially, make delete button invisible
//        buttonDelete.visibility = View.INVISIBLE
//
//        // Handle checkbox check/uncheck to show/hide delete button
//        checkBox.setOnCheckedChangeListener { _, isChecked ->
//            buttonDelete.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
//        }
//
//        // Handle delete button click
//        buttonDelete.setOnClickListener {
//            // Get the corresponding grocery item ID from the groceryItemIds list
//            val groceryItemId = groceryItemIds[position]
//
//            // Delete item from the database using the correct ID
//            val deleted = dbHelper.deleteGroceryItem(groceryItemId)
//            if (deleted) {
//                // Remove the item from the list
//                items.removeAt(position)
//                groceryItemIds.removeAt(position)  // Also remove the ID from the list
//                notifyDataSetChanged()
//            }
//        }
//
//        return view
//    }
//}
