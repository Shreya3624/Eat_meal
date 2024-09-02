package com.example.eat_easy

import android.content.Context
import android.content.SharedPreferences

class Shareprefrence (context: Context){

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    fun setlogin(islogin : Boolean):Boolean{
        val edit  = sharedPreferences.edit()
        if(islogin){

        edit.putBoolean("islogin",islogin)
        edit.apply()
        return true
        }
        else
        {
            edit.clear()
            return false
        }
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

}