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
import androidx.lifecycle.viewModelScope
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
        viewModel.fetchResumeVisible()
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
        //----삭제될 코드--------------------
//        binding.bell.setOnClickListener{
//            val intent = Intent(context,ResumeCompleteActivity::class.java)
//            intent.putExtra("setting1","public")
//            intent.putExtra("setting2","publicResume")
//            intent.putExtra("UID_KEY","FVdTauf34XeQPsRN1H0h99nEbgz1")
//            startActivity(intent)
//        }

        binding.recycler.adapter = adapter

        if(viewModel.isResume.value == true){
            binding.yesResume.visibility = View.VISIBLE
        }
        else if(viewModel.isResume.value == false){
            binding.nonResume.visibility = View.VISIBLE
        }
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
}