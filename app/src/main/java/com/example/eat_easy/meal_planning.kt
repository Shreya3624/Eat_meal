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
    private lateinit var shared: Shareprefrence
    private lateinit var editButton:Button

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
        shared = Shareprefrence(requireContext())

        // Initialize database helper
        dbHelper = DataBase(requireContext())

        // Fetch last selected date from SharedPreferences
        selectedDate = shared.getLastSelectedDate() ?: ""

        // If there's a saved meal plan for the date, populate the layout
        if (selectedDate.isNotEmpty()) {
            mealDate.text = selectedDate
            fetchAndDisplayMealPlan(selectedDate)
        }

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

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    private fun mealDesign() {
        val mealDesign: View = LayoutInflater.from(requireContext()).inflate(R.layout.meal_design, layoutContainer, false)

        val delete: Button = mealDesign.findViewById(R.id.delete)
        val morning: Spinner = mealDesign.findViewById(R.id.Breakfast_spi)
        val lunch: Spinner = mealDesign.findViewById(R.id.launch_spi)
        val dinner: Spinner = mealDesign.findViewById(R.id.dinner_spi)
        saveMealButton = mealDesign.findViewById(R.id.save)
         editButton = mealDesign.findViewById(R.id.Edit)
        editButton.visibility = View.INVISIBLE

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
        val selectedBreakfast = morning.selectedItem.toString()
        val selectedLunch = lunch.selectedItem.toString()
        val selectedDinner = dinner.selectedItem.toString()

        // Handle delete button click
        delete.setOnClickListener {
            addLayout.visibility = View.VISIBLE
            layoutContainer.removeView(mealDesign)
            deleteMeals()
        }

        layoutContainer.addView(mealDesign)

        // Set up the save button to save the meal plan
        saveMealButton.setOnClickListener {
            if (saveMeal()) {
                saveMealButton.visibility = View.INVISIBLE
                editButton.visibility = View.VISIBLE
            }
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
                shared.saveLastSelectedDate(selectedDate)  // Save selected date to SharedPreferences
             //   fetchAndDisplayMealPlan(selectedDate) // Fetch the meal plan when the date is selected
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun fetchAndDisplayMealPlan(date: String) {
        // Fetch meal data from the database

        val userId = shared.getid() ?: return
        val mealPlan = dbHelper.getMealPlan(userId, date)

        // If a meal plan exists, populate the spinners
        if (mealPlan != null) {
            mealDesign()

            val mealDesign: View? = layoutContainer.getChildAt(0)
            if (mealDesign != null) {
                val morning: Spinner = mealDesign.findViewById(R.id.Breakfast_spi)
                val lunch: Spinner = mealDesign.findViewById(R.id.launch_spi)
                val dinner: Spinner = mealDesign.findViewById(R.id.dinner_spi)
                addLayout.visibility=View.GONE
                saveMealButton.visibility = View.INVISIBLE
                editButton.visibility = View.VISIBLE

                morning.setSelection((morning.adapter as ArrayAdapter<String>).getPosition(mealPlan.getString("breakfast")))
                lunch.setSelection((lunch.adapter as ArrayAdapter<String>).getPosition(mealPlan.getString("lunch")))
                dinner.setSelection((dinner.adapter as ArrayAdapter<String>).getPosition(mealPlan.getString("dinner")))
            }
        }
    }

    private fun saveMeal(): Boolean {
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

            val userId = shared.getid()
            var success = false
            if (userId != null) {
                success = dbHelper.addMeal(userId, selectedDate, selectedBreakfast, selectedLunch, selectedDinner)
            }

            // Save the meal to the database
            if (success) {
                Toast.makeText(requireContext(), "Meal plan saved successfully!", Toast.LENGTH_SHORT).show()
                return true
            } else {
                Toast.makeText(requireContext(), "Failed to save meal plan.", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }
    private fun deleteMeals(){
        val userId = shared.getid() ?: return
        val rowsDeleted= dbHelper.deleteMeal(userId,selectedDate)
        if (rowsDeleted > 0) {
            Toast.makeText(requireContext(), "Meal deleted successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Meal please fill", Toast.LENGTH_SHORT).show()
        }
    }
    private fun upadatemeal(userId:Int,Date:String,Breakfast:String,lunch:String,dinner:String){
        val success = dbHelper.updateMeal(userId, Date, Breakfast, lunch, dinner)
        if (success) {
            Toast.makeText(context, "Meal updated successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to update meal", Toast.LENGTH_SHORT).show()
        }
    }
}
