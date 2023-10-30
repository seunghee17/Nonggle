package com.example.nongglenonggle.presentation.view.worker.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentWorkerHomeBinding
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.view.worker.resume.ResumeActivity

class WorkerHomeFragment : BaseFragment<FragmentWorkerHomeBinding>(R.layout.fragment_worker_home) {

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
    }

}