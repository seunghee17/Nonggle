package com.capstone.nongglenonggle.presentation.view.farmer.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.presentation.view.farmer.mypage.FarmerMypageFragment
import com.capstone.nongglenonggle.presentation.view.farmer.notice.NoticeFragment
import com.capstone.nongglenonggle.presentation.view.farmer.search.SearchWorkerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavi = findViewById<BottomNavigationView>(R.id.bottom_navi)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController


        navController.addOnDestinationChangedListener{_,destination,_->
            if(destination.id == R.id.farmerhomeFragment || destination.id==R.id.searchWorkerFragment || destination.id == R.id.farmerMypageFragment){
                bottomNavi.visibility = View.VISIBLE
            }else{
                bottomNavi.visibility = View.GONE
            }
        }
        bottomNavi.setupWithNavController(navController)
    }

}
