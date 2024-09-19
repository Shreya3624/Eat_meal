package com.example.eat_easy

import android.content.Context
import android.content.SharedPreferences

class Shareprefrence (context: Context){

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    fun setlogin(islogin : Boolean) {
        val edit = sharedPreferences.edit()

        edit.putBoolean("islogin", islogin)
        edit.apply()

    }
   fun getLogin ():Boolean{
      return sharedPreferences.getBoolean("islogin",false)
    }

    fun storeEmail(email : String){
        val edit = sharedPreferences.edit()
        edit.putString("emailId",email)
        edit.apply()
    }

    fun getEmail () : String? {
        return sharedPreferences.getString("emailId","hello")
    }
    fun setid(id:Int){
        val edit = sharedPreferences.edit()
        edit.putInt("userid",id)
        edit.apply()
    }
    fun getid (): Int?{
        return   sharedPreferences.getInt("userid",1)
    }
    fun saveLastSelectedDate (date : String){
        val edit=sharedPreferences.edit()
        edit.putString("Date",date)
        edit.apply()
    }
    fun getLastSelectedDate(): String? {
        return sharedPreferences.getString("Date","")
    }
    fun setbmi(bmi:Int){
        val edit = sharedPreferences.edit()
        edit.putInt("bmi",bmi)
        edit.apply()
    }
    fun getbmi():Int{
        return sharedPreferences.getInt("bmi", 1)
    }
    fun logout() {
        val edit = sharedPreferences.edit()
        edit.remove("Date")  // Clears the last selected date on logout
        edit.apply()

        // Add any other logout actions, like returning to the login screen
    }

}