package com.capstone.nongglenonggle.presentation.view.farmer.notice

import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.presentation.base.BaseActivity
import com.capstone.nongglenonggle.databinding.ActivityNoticeBinding
import com.capstone.nongglenonggle.presentation.view.adapter.ViewPager2Adapter
import com.capstone.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeActivity : BaseActivity<ActivityNoticeBinding>(R.layout.activity_notice) {
    private val viewModel: FarmerNoticeViewModel by viewModels()
    private lateinit var tablayout : TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        tablayout = binding.tablayout

        binding.closebtn.setOnClickListener{
            finish()
        }

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