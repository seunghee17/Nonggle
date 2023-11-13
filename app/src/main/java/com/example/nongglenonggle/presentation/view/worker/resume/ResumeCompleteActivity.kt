package com.example.nongglenonggle.presentation.view.worker.resume

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.ActivityResumeCompleteBinding
import com.example.nongglenonggle.domain.entity.Model
import com.example.nongglenonggle.presentation.base.BaseActivity
import com.example.nongglenonggle.presentation.view.adapter.ResumeCareerAdapter
import com.example.nongglenonggle.presentation.viewModel.farmer.FarmerNoticeCompleteViewModel
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeCompleteViewModel
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumeCompleteActivity : BaseActivity<ActivityResumeCompleteBinding>(R.layout.activity_resume_complete) {
    private val viewModel : ResumeCompleteViewModel by viewModels()
    private lateinit var  adapter: ResumeCareerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.view.visibility = View.VISIBLE
        val allCareer = mutableListOf<Model.ResumeSummary>()

        adapter = ResumeCareerAdapter(emptyList())
        val recycler = binding.recyclerview
        recycler.adapter = adapter

        val value1 = intent.getStringExtra("setting1") ?: return
        val value2 = intent.getStringExtra("setting2") ?: return
        viewModel.fetchResumeDetail(value1, value2)


        viewModel.resumeDetail.asLiveData().observe(this, Observer{data->
            data?.resumeData?.let{resumeData->
//                allCareer.clear()
//                allCareer.addAll(resumeData)
//                if(::adapter.isInitialized){
//                    adapter.updatelist(allCareer)
//                    Log.d("ResumeCompleteActivity", "update success $resumeData")
//                }
//                else{
//                    adapter = ResumeCareerAdapter(allCareer)
//                    recycler.adapter = adapter
//                }
                if(resumeData.isNotEmpty()){
                    allCareer.clear()
                    allCareer.addAll(resumeData)
                    adapter.updatelist(allCareer)
                    Log.e("Resume", "$allCareer")
                }
                else{
                    Log.e("Resume","erroereroejroe")
                }
            }
        })
    }

}