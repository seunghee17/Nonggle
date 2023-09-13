package com.example.nongglenonggle.view.farmer.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.ActivityNoticeBinding
import com.google.android.material.tabs.TabLayout
import kotlin.reflect.KMutableProperty1

class NoticeActivity : AppCompatActivity() {
    private var _binding : ActivityNoticeBinding? = null
    private val binding get() = _binding!!
    private lateinit var tablayout : TabLayout
    private lateinit var framelayout:FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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