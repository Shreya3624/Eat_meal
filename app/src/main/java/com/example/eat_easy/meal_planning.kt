package com.example.eat_easy

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class meal_planning : Fragment() {
    private lateinit var mealDate: TextView
    private lateinit var addLayout: Button
    private lateinit var layoutContainer: LinearLayout
    private lateinit var saveMealButton: Button
    private lateinit var dbHelper: DataBase
    private lateinit var shared: Shareprefrence
    private lateinit var editButton: Button
    private lateinit var morning:Spinner
    private lateinit var lunch:Spinner
    private lateinit var dinner:Spinner

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

        // Check if 24 hours have passed and clear/reset meal data if necessary
        clearDataIf24HoursPassed()

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
            val systemDate=System.currentTimeMillis()
            val dateFormat=SimpleDateFormat("dd//mm/yyyy",Locale.getDefault())
            val currentDate=dateFormat.format(Date(systemDate))
            if (currentDate!=selectedDate){
                layoutContainer.removeAllViews()
                addLayout.visibility=View.VISIBLE
            }

        }

        return view
    }

    @SuppressLint("MissingInflatedId")
    private fun mealDesign(): Boolean {
        val mealDesign: View = LayoutInflater.from(requireContext()).inflate(R.layout.meal_design, layoutContainer, false)
        val delete: Button = mealDesign.findViewById(R.id.delete)
        val morning: Spinner = mealDesign.findViewById(R.id.Breakfast_spi)
        val lunch: Spinner = mealDesign.findViewById(R.id.launch_spi)
        val dinner: Spinner = mealDesign.findViewById(R.id.dinner_spi)
        val bmitxt:TextView=mealDesign.findViewById(R.id.bmitxt)
        saveMealButton = mealDesign.findViewById(R.id.save)
        editButton = mealDesign.findViewById(R.id.Edit)
        editButton.visibility = View.INVISIBLE
        val bmi=shared.getbmi()
        if (bmi!=null) {
            bmitxt.text = bmifunction(bmi)
        }
       val meals= getMealsBasedOnBMI(bmi)
        val breakfastItems = meals["breakfast"] ?: listOf()
        val lunchItems = meals["lunch"] ?: listOf()
        val dinnerItems = meals["dinner"] ?: listOf()

        // Update the spinners with the new meal lists
        val breakfastAdapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, breakfastItems)
        val lunchAdapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, lunchItems)
        val dinnerAdapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_item, dinnerItems)

        // Assuming morning, lunch, and dinner are the Spinner views in your layout
        morning.adapter = breakfastAdapter
        lunch.adapter = lunchAdapter
        dinner.adapter = dinnerAdapter
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
        editButton.setOnClickListener {
            // Show confirmation dialog
            val dialogBuilder = android.app.AlertDialog.Builder(requireContext())
            dialogBuilder.setMessage("Do you want to update the meal plan?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    // User clicked Yes, proceed to update the meal plan
                    val userId = shared.getid() ?: return@setPositiveButton
                    val selectedBreakfast = morning.selectedItem.toString()
                    val selectedLunch = lunch.selectedItem.toString()
                    val selectedDinner = dinner.selectedItem.toString()
                    updateMeal(userId, selectedDate, selectedBreakfast, selectedLunch, selectedDinner)
                }
                .setNegativeButton("No") { dialog, id ->
                    // User clicked No, dismiss the dialog
                    dialog.dismiss()
                }
            // Create and show the dialog
            val alert = dialogBuilder.create()
            alert.setTitle("Update Meal Plan")
            alert.show()
        }


        return true
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
                fetchAndDisplayMealPlan(selectedDate) // Fetch the meal plan when the date is selected
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
                addLayout.visibility = View.GONE
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
        return false
    }

    private fun deleteMeals() {
        val userId = shared.getid() ?: return
        val rowsDeleted = dbHelper.deleteMeal(userId, selectedDate)
        if (rowsDeleted > 0) {
            Toast.makeText(requireContext(), "Meal deleted successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "No meal found to delete", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateMeal(userId: Int, date: String, breakfast: String, lunch: String, dinner: String) {
        val success = dbHelper.updateMeal(userId, date, breakfast, lunch, dinner)
        if (success) {
            Toast.makeText(context, "Meal updated successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to update meal", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getMealsBasedOnBMI(bmi:Int): Map<String, List<String>> {
        if (bmi != null) {
            return when {
                bmi < 18 -> mapOf(
                    "breakfast" to listOf("High-Calorie Oats", "Avocado Toast", "Full-Fat Yogurt", "Nut Butter Toast"),
                    "lunch" to listOf("Paneer Curry", "High-Calorie Veg Sabji", "Veg Pulao with Butter", "Dal Makhani"),
                    "dinner" to listOf("Full-Fat Dal", "Butter Chicken (Veg)", "Heavy Rajma Chawal", "Dosa with Coconut Chutney")
                )
                bmi in 18..24 -> mapOf(
                    "breakfast" to listOf("Masala Oats", "Veggie Omelette", "Wholegrain Toast", "Fruit Smoothie"),
                    "lunch" to listOf("Mixed Veg Sabji", "Tadka Dal", "Vegetable Pulao", "Kadhi with Brown Rice"),
                    "dinner" to listOf("Masoor Dal", "Moong Dal Chilla", "Rajma Chawal", "Vegetable Idli with Sambar")
                )
                else -> mapOf(
                    "breakfast" to listOf("Low-Calorie Smoothie", "Fruit Salad", "Low-Calorie Oats", "Boiled Eggs"),
                    "lunch" to listOf("Grilled Veg Salad", "Sprouts Salad", "Brown Rice Pulao", "Light Tofu Curry"),
                    "dinner" to listOf("Light Dal Soup", "Moong Soup", "Salad with Rajma", "Steamed Veggies with Idli")
                )
            }
        }
        return mapOf(
            "clear" to listOf("nothing")
        )
    }
    private fun bmifunction(bmi: Int):String{
        return when {
            bmi < 18 -> "BMI Category:Underweight :$bmi"
            bmi in 18..24-> "BMI Category:Normal weight :$bmi"
            bmi in 25..29 -> "BMI Category:Overweight :$bmi"
            else -> "Obesity$bmi"
        }
    }
    private fun clearDataIf24HoursPassed() {
        val sharedPreferences = requireContext().getSharedPreferences("MealPlannerPrefs", android.content.Context.MODE_PRIVATE)
        val lastAccessDate = sharedPreferences.getLong("lastAccessDate", 0L)
        val currentDate = System.currentTimeMillis()

        // Check if 24 hours (86400000 milliseconds) have passed
        if (currentDate - lastAccessDate >= 86400000) {
            // Clear meal data and reset date
            clearMealData()

            // Update the last access date in SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putLong("lastAccessDate", currentDate)
            editor.apply()
        }
    }

    private fun clearMealData() {
        // Clear meal data from UI or database
        layoutContainer.removeAllViews() // Clear all the dynamically added views

        addLayout.visibility = View.VISIBLE // Re-enable the add button
        mealDate.text = "" // Clear the date
        shared.saveLastSelectedDate("") // Clear the saved date in SharedPreferences

    }
}
