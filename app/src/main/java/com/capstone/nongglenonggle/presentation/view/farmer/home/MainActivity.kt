package com.capstone.nongglenonggle.presentation.view.farmer.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,FarmerhomeFragment())
            .commit()

        bottomNavi.setOnItemSelectedListener { item->
            var selectedFragment: Fragment?=null
            when(item.itemId){
                R.id.home_farmer -> {
                    selectedFragment=FarmerhomeFragment()
                }
                //일손찾기
                R.id.find_farmer->{
                    selectedFragment= SearchWorkerFragment()
                }
                //공고쓰기
                R.id.write_farmer->{
                    selectedFragment= NoticeFragment()
                }
                R.id.mypage_farmer->{
                    selectedFragment= FarmerMypageFragment()
                }

            }
            if(selectedFragment != null){
                supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit()
            }
            true
        }
    }
    fun hideBottomNavi(){
        val bottomNavi = findViewById<BottomNavigationView>(R.id.bottom_navi)
        bottomNavi.visibility = View.GONE
    }

    fun showBottomNavigation() {
        val bottomNavi = findViewById<BottomNavigationView>(R.id.bottom_navi)
        bottomNavi.visibility = View.VISIBLE
    }

}
