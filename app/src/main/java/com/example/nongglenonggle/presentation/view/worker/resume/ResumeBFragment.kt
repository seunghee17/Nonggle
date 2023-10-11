package com.example.nongglenonggle.presentation.view.worker.resume

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentResumeBBinding
import com.example.nongglenonggle.presentation.base.BaseFragment
import com.example.nongglenonggle.presentation.view.dialog.CareerAddFragment
import com.example.nongglenonggle.presentation.view.dialog.DatepickerFragment
import com.example.nongglenonggle.presentation.viewModel.worker.ResumeViewModel


class ResumeBFragment : BaseFragment<FragmentResumeBBinding>(R.layout.fragment_resume_b) {
    private val viewModel: ResumeViewModel by activityViewModels()

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
        binding.viewModel = viewModel

        binding.nextBtn.setOnClickListener{
            val viewpager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
            val current = viewpager.currentItem
            val next = current+1
            if(next < viewpager.adapter?.itemCount ?: 0){
                viewpager.setCurrentItem(next,true)
            }else{
                Log.e("yet","아직 마지막아님")
            }
        }

        binding.addCareer.setOnTouchListener{view,event->
            if(event.action == MotionEvent.ACTION_UP){
                showDatePicker()
            }
            false
        }


    }
    private fun showDatePicker() {
        val newFrament = CareerAddFragment()
        newFrament.show(parentFragmentManager,"careerAdd")
    }

}