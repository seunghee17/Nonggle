package com.example.nongglenonggle.presentation.view.worker.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentWorkerSearchBinding
import com.example.nongglenonggle.domain.entity.SeekerHomeFilterContent
import com.example.nongglenonggle.domain.entity.WorkerHomeData
import com.example.nongglenonggle.domain.entity.WorkerSearchRecommend
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.view.adapter.FilterWorkerHomeAdapter
import com.example.nongglenonggle.presentation.view.adapter.WorkerSearchAdapter
import com.example.nongglenonggle.presentation.view.dialog.FilterBottomSheetFragment
import com.example.nongglenonggle.presentation.view.farmer.notice.NoticeCompleteActivity
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeCompleteViewModel
import com.example.nongglenonggle.presentation.viewModel.worker.WorkerHomeViewModel
import com.example.nongglenonggle.presentation.viewModel.worker.WorkerSearchViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WorkerSearchFragment : BaseFragment<FragmentWorkerSearchBinding>(R.layout.fragment_worker_search) {
    private val viewModel : WorkerSearchViewModel by activityViewModels()
    private val viewModelHM : WorkerHomeViewModel by viewModels()
    private val viewModelCM : ResumeCompleteViewModel by activityViewModels()
    private lateinit var adapter1: FilterWorkerHomeAdapter
    private lateinit var adapter2: WorkerSearchAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        adapter1 = FilterWorkerHomeAdapter(emptyList(), object :
            FilterWorkerHomeAdapter.onItemClickListener {
            override fun onItemClick(uid:String) {
                val intent = Intent(requireContext(), NoticeCompleteActivity::class.java)
                Log.d("onItemClickListener",uid)
                intent.putExtra("UID_KEY", uid)
                startActivity(intent)
            }

        })

        adapter2 = WorkerSearchAdapter(mutableListOf(), object :
            WorkerSearchAdapter.onItemClickListener {
            override fun onItemClick(uid:String) {
                val intent = Intent(requireContext(), NoticeCompleteActivity::class.java)
                Log.d("onItemClickListener",uid)
                intent.putExtra("UID_KEY", uid)
                startActivity(intent)
            }

        })

        binding.mainRecycler.adapter = adapter1
        binding.subRecycler.adapter = adapter2

        binding.worktype.setOnClickListener{
            //bottomsheet
            setupBottomSheet()
        }

        viewModelCM._resumeforFarmer.postValue(true)


        viewModelHM.allNotice.observe(viewLifecycleOwner){docs->
            val noticeList = mutableListOf<SeekerHomeFilterContent>()
            //Log.d("allNotice1","$noticeList")
            for(notice in docs){
                Log.d("allNotice","$notice")
                noticeList.add(notice)
            }
            adapter1.updateList(noticeList)
        }
        viewModel.subNotice.observe(viewLifecycleOwner){docs->
            val noticeList = mutableListOf<WorkerSearchRecommend>()
            Log.d("subNotice","$noticeList")
            for(notice in docs){
                noticeList.add(notice)
            }
            adapter2.updateList(noticeList)
        }


    }
    private fun setupBottomSheet(){
        val bottomsheet = FilterBottomSheetFragment()
        bottomsheet.show(parentFragmentManager,bottomsheet.tag)
    }

}