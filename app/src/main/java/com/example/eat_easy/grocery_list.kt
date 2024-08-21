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
    private lateinit var adapter: ArrayAdapter<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_grocery_list, container, false)
        editTextItem = view.findViewById(R.id.editTextItem)
        buttonAddItem = view.findViewById(R.id.buttonAddItem)
        listViewItems =view.findViewById(R.id.listViewItems)

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, itemsList)
        listViewItems.adapter = adapter

        buttonAddItem.setOnClickListener {
            val item = editTextItem.text.toString()
            if (item.isNotEmpty()) {
                itemsList.add(item)
                adapter.notifyDataSetChanged()
                editTextItem.text.clear()  // Clear the input field after adding the item
            }
        }

        listViewItems.setOnItemClickListener { _, _, position, _ ->
            itemsList.removeAt(position)
            adapter.notifyDataSetChanged()
        }
        return view
    }



}