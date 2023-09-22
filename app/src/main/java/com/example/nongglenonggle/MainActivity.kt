package com.example.nongglenonggle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.nongglenonggle.view.farmer.home.Farmer_home
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavi = findViewById<BottomNavigationView>(R.id.bottom_navi)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,Farmer_home())
            .commit()

//        bottomNavi.setOnNavigationItemSelectedListener {
//            item->
//            var selectedFragment: Fragment?=null
//            when(item.itemId){
//
//            }
//        }
    }
    }
