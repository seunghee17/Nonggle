package com.example.nongglenonggle.view.farmer.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.nongglenonggle.R
import com.example.nongglenonggle.base.BaseActivity
import com.example.nongglenonggle.databinding.ActivityNoticeBinding
import com.example.nongglenonggle.view.adapter.ViewPager2Adapter
import com.example.nongglenonggle.viewModel.farmer.FarmerNoticeViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class NoticeActivity : BaseActivity<ActivityNoticeBinding>(R.layout.activity_notice) {
    private val viewModel: FarmerNoticeViewModel by viewModels()
    private lateinit var tablayout : TabLayout
    private lateinit var framelayout:FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //framelayout = binding.framelayout
        tablayout = binding.tablayout

        binding.closebtn.setOnClickListener{
            finish()
        }
        //데이터 받는다
//        val received = intent
//        if(received != null){
//            val receivedData = received.getStringExtra("addressData")
//            if(receivedData != null){
//                viewModel._AddressFromWeb.postValue(receivedData)
//            }
//        }

        initViewPager()
    }
    private fun initViewPager(){
        var viewPager2Adapter = ViewPager2Adapter(this)
        viewPager2Adapter.addfragment(noticeAFragment())
        viewPager2Adapter.addfragment(noticeBFragment())
        viewPager2Adapter.addfragment(noticeCFragment())
        viewPager2Adapter.addfragment(noticeDFragment())

        binding.viewpager.apply {
            adapter = viewPager2Adapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
        TabLayoutMediator(binding.tablayout, binding.viewpager){
                tab,position->
            when(position){
                0 -> tab.text = "기본정보"
                1 -> tab.text = "작업내용"
                2 -> tab.text = "고용정보"
                3 -> tab.text = "추가내용"
            }
        }.attach()

    }
}