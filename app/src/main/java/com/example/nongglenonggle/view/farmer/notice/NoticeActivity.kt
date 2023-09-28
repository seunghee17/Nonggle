package com.example.nongglenonggle.view.farmer.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.ActivityNoticeBinding
import com.example.nongglenonggle.viewModel.farmer.FarmerNoticeViewModel
import com.google.android.material.tabs.TabLayout

class NoticeActivity : AppCompatActivity() {
    private var _binding : ActivityNoticeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FarmerNoticeViewModel by viewModels()
    private lateinit var tablayout : TabLayout
    private lateinit var framelayout:FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityNoticeBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)


        framelayout = findViewById(R.id.framelayout)
        tablayout = findViewById(R.id.tablayout)


        //초기 화면 세팅
        supportFragmentManager.beginTransaction()
            .replace(R.id.framelayout, noticeAFragment())
            .commit()


        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position){
                   0 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout,noticeAFragment())
                        .commit()
                    1 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout,noticeBFragment())
                        .commit()
                    2-> supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout,noticeCFragment())
                        .commit()
                    3 -> supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout,noticeDFragment())
                        .commit()

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        viewModel.setFragment.observe(this, Observer{
            if(it)
            {
                val nextTabPosition = tablayout.selectedTabPosition +1
                if(nextTabPosition < tablayout.tabCount)
                {
                    tablayout.getTabAt(nextTabPosition)?.select()
                }
                viewModel.switchTab.value = false
            }
        })



    }
}