package com.example.nongglenonggle.view.farmer.notice

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nongglenonggle.R
import com.example.nongglenonggle.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentNoticeBinding

class NoticeFragment : BaseFragment<FragmentNoticeBinding>(R.layout.fragment_notice) {
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

        binding.gotoN.setOnClickListener{
            val intent = Intent(context, NoticeActivity::class.java)
            startActivity(intent)
        }
    }

}