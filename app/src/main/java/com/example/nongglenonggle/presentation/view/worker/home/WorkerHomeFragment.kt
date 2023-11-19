package com.example.nongglenonggle.presentation.view.worker.home

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentWorkerHomeBinding
import com.example.nongglenonggle.domain.entity.SeekerHomeFilterContent
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.view.adapter.FilterWorkerHomeAdapter
import com.example.nongglenonggle.presentation.view.farmer.notice.NoticeCompleteActivity
import com.example.nongglenonggle.presentation.view.login.LoginActivity
import com.example.nongglenonggle.presentation.view.worker.resume.ResumeActivity
import com.example.nongglenonggle.presentation.view.worker.resume.ResumeCompleteActivity
import com.example.nongglenonggle.presentation.viewModel.worker.WorkerHomeViewModel
import com.google.api.Distribution.BucketOptions.Linear
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkerHomeFragment : BaseFragment<FragmentWorkerHomeBinding>(R.layout.fragment_worker_home) {
    private val viewModel : WorkerHomeViewModel by viewModels()
    private lateinit var adapter: FilterWorkerHomeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchUserInfo()
        //viewModel.fetchResumeVisible()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= super.onCreateView(inflater, container, savedInstanceState)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        adapter = FilterWorkerHomeAdapter(emptyList(), object :
            FilterWorkerHomeAdapter.onItemClickListener {
            override fun onItemClick(uid:String) {
                val intent = Intent(requireContext(), NoticeCompleteActivity::class.java)
                Log.d("onItemClickListener",uid)
                intent.putExtra("UID_KEY", uid)
                startActivity(intent)
            }

        })


        binding.recycler.adapter = adapter

        binding.bell.setOnClickListener{
            findNavController().navigate(R.id.alarmFragment)
        }

        viewModel.userDetail.observe(viewLifecycleOwner, Observer{userDetail ->
            if(userDetail?.refs?.isNotEmpty() == true){
                //공고글 있을때
                viewModel.viewModelScope.launch {
                    viewModel._isResume.value = true
                    val data = viewModel.setUserFromRef(userDetail?.refs!!.get(0))
                    viewModel._homeResume.value = data
                }
            }
            else{
                viewModel._isResume.value = false
            }

        })

        viewModel.homeResume.observe(viewLifecycleOwner, Observer{homeResume->
            binding.yesResume.title.text = homeResume.userPresent
            binding.yesResume.wantLocation.text = "희망지역  ${homeResume.locationSelect.get(0)} ${homeResume.locationSelect.get(1)}"
            setTextColor1(binding.yesResume.wantLocation,binding.yesResume.wantLocation.text.toString(),"${homeResume.locationSelect.get(0)} ${homeResume.locationSelect.get(1)}")
            binding.yesResume.wantCategory.text = "희망품목 ${homeResume.desiredItem?.get(0)},${homeResume.desiredItem?.get(1)},${homeResume.desiredItem?.get(2)}"
            setTextColor1(binding.yesResume.wantCategory,binding.yesResume.wantCategory.text.toString(),"${homeResume.desiredItem?.get(0)},${homeResume.desiredItem?.get(1)},${homeResume.desiredItem?.get(2)}")
        })


        setTextColor(binding.tipbox,binding.tipbox.text.toString(), "Tip!")

        val writeBtn: LinearLayout = binding.root.findViewById(R.id.write_resume_btn)
        writeBtn.setOnClickListener{
            val intent = Intent(requireContext(), ResumeActivity::class.java)
            startActivity(intent)
        }

        binding.logo.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        viewModel.allNotice.observe(viewLifecycleOwner){docs->
            val noticeList = mutableListOf<SeekerHomeFilterContent>()
            for(notice in docs){
                noticeList.add(notice)
            }
            adapter.updateList(noticeList)
            viewModel.updateVisible()
        }

        val imageView = binding.imageView
        val displayMetrics = Resources.getSystem().displayMetrics
        val screenHeight = displayMetrics.heightPixels
        val imageViewHeight = screenHeight / 2.3

        val layoutParams = imageView.layoutParams
        layoutParams.height = imageViewHeight.toInt()
        imageView.layoutParams = layoutParams
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

    private fun setTextColor1(textView: TextView, fullText:String, wordsToColor:String){
        val color = ContextCompat.getColor(textView.context, R.color.m1)
        val spannableStringBuilder = SpannableStringBuilder(fullText)
        var startIndex = fullText.indexOf(wordsToColor)
        while(startIndex != -1){
            val endIndex = startIndex + wordsToColor.length
            spannableStringBuilder.setSpan(ForegroundColorSpan(color), startIndex,endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            startIndex = fullText.indexOf(wordsToColor, startIndex+1)
        }
        textView.text = spannableStringBuilder
    }
}