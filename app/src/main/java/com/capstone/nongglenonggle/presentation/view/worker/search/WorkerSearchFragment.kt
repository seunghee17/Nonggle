package com.capstone.nongglenonggle.presentation.view.worker.search

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.FragmentWorkerSearchBinding
import com.capstone.nongglenonggle.domain.entity.SeekerHomeFilterContent
import com.capstone.nongglenonggle.domain.entity.WorkerSearchRecommend
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.presentation.view.adapter.FilterWorkerHomeAdapter
import com.capstone.nongglenonggle.presentation.view.adapter.WorkerSearchAdapter
import com.capstone.nongglenonggle.presentation.view.dialog.FilterBottomSheetFragment
import com.capstone.nongglenonggle.presentation.view.farmer.notice.NoticeCompleteActivity
import com.capstone.nongglenonggle.presentation.viewModel.worker.ResumeCompleteViewModel
import com.capstone.nongglenonggle.presentation.viewModel.worker.WorkerHomeViewModel
import com.capstone.nongglenonggle.presentation.viewModel.worker.WorkerSearchViewModel
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
                intent.putExtra("UID_KEY", uid)
                startActivity(intent)
            }

        })

        adapter2 = WorkerSearchAdapter(mutableListOf(), object :
            WorkerSearchAdapter.onItemClickListener {
            override fun onItemClick(uid:String) {
                val intent = Intent(requireContext(), NoticeCompleteActivity::class.java)
                intent.putExtra("UID_KEY", uid)
                startActivity(intent)
            }

        })

        binding.mainRecycler.adapter = adapter1
        binding.subRecycler.adapter = adapter2

        binding.worktype.setOnClickListener{
            setupBottomSheet()
        }

        viewModelCM._resumeforFarmer.postValue(true)


        viewModelHM.allNotice.observe(viewLifecycleOwner){docs->
            val noticeList = mutableListOf<SeekerHomeFilterContent>()
            for(notice in docs){
                noticeList.add(notice)
            }
            adapter1.updateList(noticeList)
        }
        viewModel.subNotice.observe(viewLifecycleOwner){docs->
            val noticeList = mutableListOf<WorkerSearchRecommend>()
            for(notice in docs){
                noticeList.add(notice)
            }
            adapter2.updateList(noticeList)
        }


    }
    private fun setTextColor(textView: TextView, fullText:String, wordsToColor:String){
        val color = ContextCompat.getColor(textView.context, R.color.s1)
        val spannableStringBuilder = SpannableStringBuilder(fullText)
        var startIndex = fullText.indexOf(wordsToColor)
        while(startIndex != -1){
            val endIndex = startIndex + wordsToColor.length
            spannableStringBuilder.setSpan(ForegroundColorSpan(color), startIndex,endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            startIndex = fullText.indexOf(wordsToColor, startIndex+1)
        }
        textView.text = spannableStringBuilder
    }
    private fun setupBottomSheet(){
        val bottomsheet = FilterBottomSheetFragment()
        bottomsheet.show(parentFragmentManager,bottomsheet.tag)
    }

}