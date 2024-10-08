package com.example.eat_easy

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle

class DataBase(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "userDatabase.db"
        private const val DATABASE_VERSION = 5
//USER DATA TABLE
        const val TABLE_USERS = "Users"
        private const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        private const val COLUMN_CREATED_AT="created_at"
        const val COLUMN_WEIGHT="weight"
        const val COLUMN_HEIGHT ="height"
        const val COLUMN_BMI="bmi"
//TABLE GROCERY_LIST
 private const val TABLE_GROCERY_LIST = "GroceryList"
private const val COLUMN_GROCERY_LIST_ID = "groceryList_id"
private const val COLUMN_USER_ID = "user_id"
private const val COLUMN_GROCERY_NAME = "grocery_name"
private const val COLUMN_G_CREATED_AT = "created_at"
        // TABLE MEALS
        private const val TABLE_MEALS = "Meals"
        private const val COLUMN_MEAL_ID = "meal_id"
        private const val COLUMN_MEAL_DATE = "meal_date"
        private const val COLUMN_BREAKFAST = "breakfast"
        private const val COLUMN_LUNCH = "lunch"
        private const val COLUMN_DINNER = "dinner"
        private const val COLUMN_MEAL_CREATED_AT = "created_at"

        private const val TABLE_CREATE =
            "CREATE TABLE $TABLE_USERS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_USERNAME TEXT NOT NULL, " +
                    "$COLUMN_EMAIL TEXT NOT NULL UNIQUE, " +
                    "$COLUMN_PASSWORD TEXT NOT NULL, " +
                    "$COLUMN_WEIGHT INTEGER NOT NULL, " +
                    "$COLUMN_HEIGHT INTEGER NOT NULL, " +
                    "$COLUMN_BMI TEXT NOT NULL, " +
                    "$COLUMN_CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ");"

        private const val TABLE_CREATE_GROCERY_LIST =
            "CREATE TABLE $TABLE_GROCERY_LIST (" +
                    "$COLUMN_GROCERY_LIST_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_USER_ID INTEGER, " +
                    "$COLUMN_GROCERY_NAME TEXT NOT NULL, " +
                    "$COLUMN_CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY ($COLUMN_USER_ID) REFERENCES $TABLE_USERS($COLUMN_ID)" +
                    ");"
        private const val TABLE_CREATE_MEALS =
            "CREATE TABLE $TABLE_MEALS (" +
                    "$COLUMN_MEAL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_USER_ID INTEGER, " +
                    "$COLUMN_MEAL_DATE DATE NOT NULL, " +
                    "$COLUMN_BREAKFAST TEXT NOT NULL, " +
                    "$COLUMN_LUNCH TEXT NOT NULL, " +
                    "$COLUMN_DINNER TEXT NOT NULL, " +
                    "$COLUMN_MEAL_CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY ($COLUMN_USER_ID) REFERENCES $TABLE_USERS($COLUMN_ID)" +
                    ");"



    }
    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(TABLE_CREATE)
        db?.execSQL(TABLE_CREATE_GROCERY_LIST)
        db?.execSQL(TABLE_CREATE_MEALS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_GROCERY_LIST")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MEALS")
        onCreate(db)

    }


    fun addUser(username: String, email: String, password: String ,weight:Int , height:Int,bmi :String ) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_WEIGHT,weight)
            put(COLUMN_HEIGHT,height)
            put(COLUMN_BMI,bmi)

        }
        db.insert(TABLE_USERS, null, values)
        db.close()
    }

    fun getUserByEmail(email: String): Cursor? {
        val db = this.readableDatabase
        return db.query(
            TABLE_USERS,
            null,
            "$COLUMN_EMAIL = ?",
            arrayOf(email),
            null,
            null,
            null
        )
    }
    fun getUserByEmailAndPassword(email: String, password: String): Cursor? {
        val db = this.readableDatabase
        return db.query(
            TABLE_USERS,
            null,
            "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(email, password),
            null,
            null,
            null
        )
    }
    fun addGrocery(userId:Int,GroceryName:String):Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_ID, userId)
            put(COLUMN_GROCERY_NAME, GroceryName)
        }
       val result= db.insert(TABLE_GROCERY_LIST, null, values)
        db.close()
        return result!=-1L
    }
    fun deleteGroceryItem(groceryItemId:Int):Boolean{
        val db=this.writableDatabase
        val result=db.delete(TABLE_GROCERY_LIST,"$COLUMN_GROCERY_LIST_ID=?", arrayOf(groceryItemId.toString()))>0
        if (result) {
            db.execSQL("DELETE FROM sqlite_sequence WHERE name = '$TABLE_GROCERY_LIST'")
        }
        return result
    }
    fun getallgrocery(userId: Int): Cursor {
        val db = this.readableDatabase
        return db.rawQuery(
            "SELECT * FROM $TABLE_GROCERY_LIST WHERE $COLUMN_USER_ID=?",
            arrayOf(userId.toString())
        )
    }
    // Meal Plan Operations
    fun addMeal(userId: Int, mealDate: String, breakfast: String, lunch: String, dinner: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_ID, userId)
            put(COLUMN_MEAL_DATE, mealDate)
            put(COLUMN_BREAKFAST, breakfast)
            put(COLUMN_LUNCH, lunch)
            put(COLUMN_DINNER, dinner)
        }
        val result = db.insert(TABLE_MEALS, null, values)
        db.close()
        return result != -1L
    }

    @SuppressLint("Range")
    internal fun getMealPlan(userId: Int, date: String): Bundle? {
        val db = this.readableDatabase
        val query = """
        SELECT $COLUMN_BREAKFAST, $COLUMN_LUNCH, $COLUMN_DINNER 
        FROM $TABLE_MEALS 
        WHERE user_id = ? AND $COLUMN_MEAL_DATE = ?
    """
        val cursor = db.rawQuery(query, arrayOf(userId.toString(), date))

        if (cursor.moveToFirst()) {
            val mealPlan = Bundle()
            mealPlan.putString("breakfast",cursor.getString(cursor.getColumnIndex(COLUMN_BREAKFAST)))
            mealPlan.putString("lunch",cursor.getString(cursor.getColumnIndex(COLUMN_LUNCH)))
            mealPlan.putString("dinner",cursor.getString(cursor.getColumnIndex(COLUMN_DINNER)))

            cursor.close()
            return mealPlan
        }

        cursor.close()
        return null
    }
//    fun deleteMeal(userId: Int, date: String): Int {
//        val db = this.writableDatabase
//
//        // Delete the meal based on userId and date
//        val result = db.delete(TABLE_MEALS, "$COLUMN_USER_ID=? AND $COLUMN_MEAL_DATE=?", arrayOf(userId.toString(), date))
//
//        if (result > 0) {
//            // Check if the table is now empty after deletion
//            val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_MEALS", null)
//            if (cursor.moveToFirst()) {
//                val count = cursor.getInt(0)
//                if (count == 0) {
//                    // Reset the auto-increment if the table is empty
//                    db.execSQL("DELETE FROM sqlite_sequence WHERE name = '$TABLE_MEALS'")
//                }
//            }
//            cursor.close()
//        }
//
//        return result
//    }

        fun deleteMeal(userId: Int, date: String): Int {
        val db=this.writableDatabase
        val result=db.delete(TABLE_MEALS, "$COLUMN_USER_ID=? AND $COLUMN_MEAL_DATE=?", arrayOf(userId.toString(), date))
        if (result>0) {
            db.execSQL("DELETE FROM sqlite_sequence WHERE name = '$TABLE_MEALS'")
        }
        return result
    }
    fun updateMeal(userId: Int, date: String, breakfast: String, lunch: String, dinner: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_BREAKFAST, breakfast)
            put(COLUMN_LUNCH, lunch)
            put(COLUMN_DINNER, dinner)
        }
        val selection = "$COLUMN_USER_ID = ? AND $COLUMN_MEAL_DATE = ?"
        val selectionArgs = arrayOf(userId.toString(), date)
        val rowsAffected = db.update(TABLE_MEALS, values, selection, selectionArgs)
        return rowsAffected > 0
    }

}




