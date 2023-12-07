package com.capstone.nongglenonggle.presentation.view.worker.resume

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.ActivityResumeBinding
import com.capstone.nongglenonggle.presentation.base.BaseActivity
import com.capstone.nongglenonggle.presentation.view.adapter.ViewPager2Adapter
import com.capstone.nongglenonggle.presentation.view.dialog.LogoutDialogFragment
import com.capstone.nongglenonggle.presentation.view.dialog.OutDirectionDialogFragment
import com.capstone.nongglenonggle.presentation.viewModel.worker.ResumeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumeActivity : BaseActivity<ActivityResumeBinding>(R.layout.activity_resume) {
    private val viewModel: ResumeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.backbtn.setOnClickListener{
            showDialog()
        }
        initViewPager()
    }
    private fun initViewPager(){
        var viewPager2Adapter = ViewPager2Adapter(this)
        viewPager2Adapter.addfragment(ResumeAFragment())
        viewPager2Adapter.addfragment(ResumeBFragment())
        viewPager2Adapter.addfragment(ResumeCFragment())
        viewPager2Adapter.addfragment(ResumeDFragment())

        binding.viewPager.apply {
            adapter = viewPager2Adapter
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
        TabLayoutMediator(binding.tablayout, binding.viewPager){
            tab,position->
            when(position){
                0 -> tab.text = "기본정보"
                1-> tab.text = "경력사항"
                2-> tab.text = "자기소개"
                3-> tab.text = "희망조건"
            }
        }.attach()
    }

    private fun showDialog(){
        val dialog = OutDirectionDialogFragment()
        dialog.show(supportFragmentManager,"dialog")
    }
}