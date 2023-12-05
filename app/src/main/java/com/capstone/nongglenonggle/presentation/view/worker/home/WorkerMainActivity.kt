package com.capstone.nongglenonggle.presentation.view.worker.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.capstone.nongglenonggle.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkerMainActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_main)
        val bottomNavi = findViewById<BottomNavigationView>(R.id.bottom_navi)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener{_,destination,_->
            if(destination.id == R.id.workerHomeFragment || destination.id == R.id.workerMypageFragment || destination.id == R.id.workerSearchFragment){
                bottomNavi.visibility = View.VISIBLE
            }
            else{
                bottomNavi.visibility = View.GONE
            }
        }

        bottomNavi.setupWithNavController(navController)
    }

}