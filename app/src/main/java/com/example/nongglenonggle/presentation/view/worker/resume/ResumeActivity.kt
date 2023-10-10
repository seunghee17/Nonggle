package com.example.nongglenonggle.presentation.view.worker.resume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.ActivityResumeBinding
import com.example.nongglenonggle.presentation.base.BaseActivity
import com.example.nongglenonggle.presentation.view.adapter.ViewPager2Adapter
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeViewModel
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ResumeActivity : BaseActivity<ActivityResumeBinding>(R.layout.activity_resume) {
    private val viewModel: ResumeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.backbtn.setOnClickListener{
            finish()
        }
        initViewPager()
    }
    private fun initViewPager(){
        var viewPager2Adapter = ViewPager2Adapter(this)
        viewPager2Adapter.addfragment(ResumeAFragment())
        viewPager2Adapter.addfragment(ResumeBFragment())
        viewPager2Adapter.addfragment(ResumeCFragment())
        viewPager2Adapter.addfragment(ResumeDFragment())

        binding.viewpager.apply {
            adapter = viewPager2Adapter
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
        TabLayoutMediator(binding.tablayout, binding.viewpager){
            tab,position->
            when(position){
                0 -> tab.text = "기본정보"
                1-> tab.text = "경력사항"
                2-> tab.text = "자기소개"
                3-> tab.text = "희망조건"
            }
        }.attach()
    }
}