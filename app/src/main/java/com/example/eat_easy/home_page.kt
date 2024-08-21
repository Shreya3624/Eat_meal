@file:Suppress("DEPRECATION")

package com.example.eat_easy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class home_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val homefrg=home()
        val groceryListfrg=grocery_list()
        val mealPlanningfrg=meal_planning()
        val profilefrg=profile()
        makeCurrentFragment(home())
        val bview:(BottomNavigationView)=findViewById(R.id.bview)
        bview.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_home -> makeCurrentFragment(homefrg)
                R.id.nav_glist -> makeCurrentFragment(groceryListfrg)
                R.id.nav_mlist -> makeCurrentFragment(mealPlanningfrg)
                R.id.nav_profile -> makeCurrentFragment(profilefrg)
            }
            true
        }
    }
    private fun makeCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fview,fragment)
            commit()
        }
}