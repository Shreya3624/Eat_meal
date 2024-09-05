package com.example.eat_easy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView


class grocery_list : Fragment() {
    private lateinit var editTextItem: EditText
    private lateinit var buttonAddItem: Button
    private lateinit var listViewItems: ListView
    private val itemsList = ArrayList<String>()
    private lateinit var adapter: customlistview
    //private var removeitemindex:Int=0
    private lateinit var dbHelper: DataBase



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_grocery_list, container, false)
        editTextItem = view.findViewById(R.id.editTextItem)
        buttonAddItem = view.findViewById(R.id.buttonAddItem)
        listViewItems =view.findViewById(R.id.listViewItems)
        dbHelper= DataBase(requireContext())
        val userId=1
        loadListView(userId)
        adapter=customlistview(requireContext(),itemsList)
       listViewItems.adapter=adapter
        buttonAddItem.setOnClickListener {
            val item = editTextItem.text.toString()
            if (item.isNotEmpty()) {
                dbHelper.addGrocery(userId,item)
                loadListView(userId)
                adapter.notifyDataSetChanged()
               editTextItem.text.clear() // Clear the input field after adding the item

            }
        }

        return view
    }
    fun loadListView(Userid:Int){
        itemsList.clear()
        val cursor=dbHelper.getallgrocery(Userid)
        while (cursor.moveToNext()){
            val result=cursor.getString(cursor.getColumnIndexOrThrow("grocery_name"))
            itemsList.add(result)
        }
        cursor.close()
    }
}