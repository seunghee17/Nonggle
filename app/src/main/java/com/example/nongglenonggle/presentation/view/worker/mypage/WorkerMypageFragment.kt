package com.example.nongglenonggle.presentation.view.worker.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentWorkerMypageBinding
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.view.worker.resume.ResumeCompleteActivity

class WorkerMypageFragment : BaseFragment<FragmentWorkerMypageBinding>(R.layout.fragment_worker_mypage) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        binding.txt.setOnClickListener{
            val intent = Intent(context, ResumeCompleteActivity::class.java)
            intent.putExtra("setting1", "public")
            intent.putExtra("setting2", "publicresume")
            startActivity(intent)
        }
    }

}