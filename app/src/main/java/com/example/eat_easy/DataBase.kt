package com.example.eat_easy

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "userDatabase.db"
        private const val DATABASE_VERSION = 1
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
// private const val TABLE_GROCERY_LIST = "GroceryList"
//private const val COLUMN_GROCERY_LIST_ID = "groceryList_id"
//private const val COLUMN_USER_ID = "user_id"
//private const val COLUMN_GROCERY_NAME = "grocery_name"
//private const val COLUMN_G_CREATED_AT = "created_at"

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

//        private const val TABLE_CREATE_GROCERY_LIST =
//            "CREATE TABLE $TABLE_GROCERY_LIST (" +
//                    "$COLUMN_GROCERY_LIST_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    "$COLUMN_USER_ID INTEGER, " +
//                    "$COLUMN_GROCERY_NAME TEXT NOT NULL, " +
//                    "$COLUMN_CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
//                    "FOREIGN KEY ($COLUMN_USER_ID) REFERENCES $TABLE_USERS($COLUMN_ID)" +
//                    ");"


    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
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
//    fun addGrocery() {
//        val db = this.writableDatabase
//        val values = ContentValues().apply {
//
//
//        }
//        db.insert(TABLE_CREATE_GROCERY_LIST, null, values)
//        db.close()
//    }
}