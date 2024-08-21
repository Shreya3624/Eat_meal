package com.example.eat_easy

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.Calendar


class meal_planning : Fragment() {

    private lateinit var  mealDate:TextView
    private lateinit var addLayout:Button
    private lateinit var layoutcontainer :LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_meal_planning, container, false)
        addLayout=view.findViewById(R.id.btn_add)
        layoutcontainer=view.findViewById(R.id.container)
        mealDate =view.findViewById(R.id.meal_date)
        addLayout.setOnClickListener{
            meal_design()
           addLayout.visibility=View.INVISIBLE
        }
        mealDate.setOnClickListener {
            showDatePickerDialog()

        }
        return view
    }
    @SuppressLint("MissingInflatedId")
    fun meal_design(){
        val meal_design : View = LayoutInflater.from(requireContext()). inflate(R.layout.meal_design,layoutcontainer,false)
        val delete : Button = meal_design.findViewById(R.id.delete)
        val morining:Spinner=meal_design.findViewById(R.id.Breakfast_spi)
        val launch:Spinner=meal_design.findViewById(R.id.launch_spi)
        val dinner:Spinner=meal_design.findViewById(R.id.dinner_spi)
        val Mitems = listOf("Item 1", "Item 2", "Item 3", "Item 4")
        val Litems = listOf("Item 1", "Item 2", "Item 3", "Item 4")
        val Ditems = listOf("Item 1", "Item 2", "Item 3", "Item 4")
        val Madapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, Mitems)
         morining.adapter=Madapter
         val Ladapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, Litems)
         Ladapter.setDropDownViewResource(R.layout.custom_spinner_item)
         launch.adapter = Ladapter
         val Dadapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, Ditems)
         dinner.adapter = Dadapter
         delete.setOnClickListener{
            addLayout.visibility=View.VISIBLE
            layoutcontainer.removeView(meal_design)
//            val dialog = AlertDialog.Builder(this)
//            val dialogView = LayoutInflater.from(this).inflate(R.layout.log_out,null)
//            dialog.setView(dialogView)
//
//            val yes : Button = dialogView.findViewById(R.id.yes)
//            val no : Button = dialogView.findViewById(R.id.no)
//
//            val alertBox = dialog.create()
//
//            yes.setOnClickListener{
//                layoutcontainer.removeView(educationDetailsView)
//                if(layoutcontainer.childCount==0){
//                    save.visibility=View.GONE
//                }
//            }
//
//            no.setOnClickListener{
//                alertBox.dismiss()
//            }
//
//            alertBox.show()

        }

        layoutcontainer.addView(meal_design)
    }
    private fun showDatePickerDialog() {
        // Get the current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a DatePickerDialog instance
        val datePickerDialog = DatePickerDialog(
            requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                mealDate.text=selectedDate
            },year,month,day
        )
        datePickerDialog.show()

            }
}


