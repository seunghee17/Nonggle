package com.example.nongglenonggle.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.nongglenonggle.R
import com.google.android.material.tabs.TabLayout
import kotlin.reflect.KMutableProperty1

class NoticeActivity : AppCompatActivity() {
    private lateinit var tablayout : TabLayout
    private lateinit var framelayout:FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        framelayout = findViewById(R.id.framelayout)
        tablayout = findViewById(R.id.tablayout)


        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position){
                   /* 0 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout,noticeAFragment.newInstance(1))
*/
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }
}