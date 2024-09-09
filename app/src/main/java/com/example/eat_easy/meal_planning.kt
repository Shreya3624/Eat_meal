package com.example.eat_easy

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.Calendar

class meal_planning : Fragment() {

    private lateinit var mealDate: TextView
    private lateinit var addLayout: Button
    private lateinit var layoutContainer: LinearLayout
    private lateinit var saveMealButton: Button
    private lateinit var dbHelper: DataBase
    private lateinit var shared:Shareprefrence

    private var selectedDate: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meal_planning, container, false)

        // Initialize UI components
        addLayout = view.findViewById(R.id.btn_add)
        layoutContainer = view.findViewById(R.id.container)
        mealDate = view.findViewById(R.id.meal_date)
        shared= Shareprefrence(requireContext())



        // Initialize database helper
        dbHelper = DataBase(requireContext())

        // Set up the "Add" button to add the meal layout
        addLayout.setOnClickListener {
            mealDesign()
            addLayout.visibility = View.INVISIBLE
        }

        // Set up the date picker dialog for selecting meal date
        mealDate.setOnClickListener {
            showDatePickerDialog()
        }

        return view
    }

    @SuppressLint("MissingInflatedId")
    private fun mealDesign() {
        val mealDesign: View = LayoutInflater.from(requireContext()).inflate(R.layout.meal_design, layoutContainer, false)

        val delete: Button = mealDesign.findViewById(R.id.delete)
        val morning: Spinner = mealDesign.findViewById(R.id.Breakfast_spi)
        val lunch: Spinner = mealDesign.findViewById(R.id.launch_spi)
        val dinner: Spinner = mealDesign.findViewById(R.id.dinner_spi)
        saveMealButton=mealDesign.findViewById(R.id.save)
        val EditButton:Button=mealDesign.findViewById(R.id.Edit)
        EditButton.visibility=View.INVISIBLE

        // Populate the breakfast, lunch, and dinner spinners with meals
        val Mitems = listOf("Masala Oats", "Avocado Toast", "Veggie Omelette", "Bread-Butter")
        val Litems = listOf("Mixed Veg Sabji", "Tadka Dal", "Vegetable Pulao", "Kadhi with Brown Rice")
        val Ditems = listOf("Masoor Dal", "Moong Dal Chilla", "Rajma Chawal", "Vegetable Idli with Sambar")

        val Madapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, Mitems)
        morning.adapter = Madapter

        val Ladapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, Litems)
        lunch.adapter = Ladapter

        val Dadapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, Ditems)
        dinner.adapter = Dadapter

        // Handle delete button click
        delete.setOnClickListener {
            addLayout.visibility = View.VISIBLE
            layoutContainer.removeView(mealDesign)
        }

        layoutContainer.addView(mealDesign)
        // Set up the save button to save the meal plan
        saveMealButton.setOnClickListener {
            if (saveMeal())
            saveMealButton.visibility=View.INVISIBLE
            EditButton.visibility=View.VISIBLE
        }
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
                selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                mealDate.text = selectedDate
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun saveMeal() :Boolean{
        if (selectedDate.isEmpty()) {
            Toast.makeText(requireContext(), "Please select a date.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Get the selected meal data
        val mealDesign: View? = layoutContainer.getChildAt(0)
        if (mealDesign != null) {
            val morning: Spinner = mealDesign.findViewById(R.id.Breakfast_spi)
            val lunch: Spinner = mealDesign.findViewById(R.id.launch_spi)
            val dinner: Spinner = mealDesign.findViewById(R.id.dinner_spi)

            val selectedBreakfast = morning.selectedItem.toString()
            val selectedLunch = lunch.selectedItem.toString()
            val selectedDinner = dinner.selectedItem.toString()


            val userId =shared.getid()
            var success:Boolean=false
            if (userId!=null){
                success = dbHelper.addMeal(userId, selectedDate, selectedBreakfast, selectedLunch, selectedDinner)

            }

            // Save the meal to the database

            if (success) {
                Toast.makeText(requireContext(), "Meal plan saved successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to save meal plan.", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }
}

//package com.example.eat_easy
//
//import android.annotation.SuppressLint
//import android.app.DatePickerDialog
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ArrayAdapter
//import android.widget.Button
//import android.widget.LinearLayout
//import android.widget.Spinner
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import java.util.Calendar
//
//
//class meal_planning : Fragment() {
//
//    private lateinit var  mealDate:TextView
//    private lateinit var addLayout:Button
//    private lateinit var layoutcontainer :LinearLayout
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        val view= inflater.inflate(R.layout.fragment_meal_planning, container, false)
//        addLayout=view.findViewById(R.id.btn_add)
//        layoutcontainer=view.findViewById(R.id.container)
//        mealDate =view.findViewById(R.id.meal_date)
//        addLayout.setOnClickListener{
//            meal_design()
//           addLayout.visibility=View.INVISIBLE
//        }
//        mealDate.setOnClickListener {
//            showDatePickerDialog()
//
//        }
//        return view
//    }
//    @SuppressLint("MissingInflatedId")
//    fun meal_design(){
//        val meal_design : View = LayoutInflater.from(requireContext()). inflate(R.layout.meal_design,layoutcontainer,false)
//        val delete : Button = meal_design.findViewById(R.id.delete)
//        val morining:Spinner=meal_design.findViewById(R.id.Breakfast_spi)
//        val launch:Spinner=meal_design.findViewById(R.id.launch_spi)
//        val dinner:Spinner=meal_design.findViewById(R.id.dinner_spi)
//        val Mitems = listOf("𝐌𝐚𝐬𝐚𝐥𝐚 𝐎𝐚𝐭𝐬", "𝐀𝐯𝐨𝐜𝐚𝐝𝐨 𝐓𝐨𝐚𝐬𝐭", "𝐕𝐞𝐠𝐠𝐢𝐞 𝐎𝐦𝐞𝐥𝐞𝐭𝐭𝐞", "𝐁𝐫𝐚𝐞𝐝-𝐁𝐮𝐭𝐭𝐞𝐫")
//        val Litems = listOf("𝐌𝐢𝐱𝐞𝐝 𝐕𝐞𝐠 𝐒𝐚𝐛𝐣𝐢", "𝐓𝐚𝐝𝐤𝐚 𝐃𝐚𝐥", "𝐕𝐞𝐠𝐞𝐭𝐚𝐛𝐥𝐞 𝐏𝐮𝐥𝐚𝐨", "𝐊𝐚𝐝𝐡𝐢 𝐰𝐢𝐭𝐡 𝐁𝐫𝐨𝐰𝐧 𝐑𝐢𝐜𝐞")
//        val Ditems = listOf("𝐌𝐚𝐬𝐨𝐨𝐫 𝐃𝐚𝐥", "𝐌𝐨𝐨𝐧𝐠 𝐃𝐚𝐥 𝐂𝐡𝐢𝐥𝐥𝐚", "𝐑𝐚𝐣𝐦𝐚 𝐂𝐡𝐚𝐰𝐚𝐥", "𝐕𝐞𝐠𝐞𝐭𝐚𝐛𝐥𝐞 𝐈𝐝𝐥𝐢 𝐰𝐢𝐭𝐡 𝐒𝐚𝐦𝐛𝐡𝐚𝐫")
//        val Madapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, Mitems)
//         morining.adapter=Madapter
//         val Ladapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, Litems)
//         Ladapter.setDropDownViewResource(R.layout.custom_spinner_item)
//         launch.adapter = Ladapter
//         val Dadapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, Ditems)
//         dinner.adapter = Dadapter
//         delete.setOnClickListener{
//            addLayout.visibility=View.VISIBLE
//            layoutcontainer.removeView(meal_design)
//
//        }
//
//        layoutcontainer.addView(meal_design)
//    }
//    private fun showDatePickerDialog() {
//        // Get the current date
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//        // Create a DatePickerDialog instance
//        val datePickerDialog = DatePickerDialog(
//            requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
//                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
//                mealDate.text=selectedDate
//            },year,month,day
//        )
//        datePickerDialog.show()
//
//            }
//}
//
//
