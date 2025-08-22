package com.capstone.nongglenonggle.presentation.view.worker.resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.databinding.FragmentResumeBBinding
import com.capstone.nongglenonggle.domain.entity.ResumeSummary
import com.capstone.nongglenonggle.presentation.base.BaseFragment
import com.capstone.nongglenonggle.presentation.view.adapter.ResumeAdapter
import com.capstone.nongglenonggle.presentation.view.dialog.CareerAddFragment


class ResumeBFragment : BaseFragment<FragmentResumeBBinding>(R.layout.fragment_resume_b) {
    private lateinit var resumeAdapter : ResumeAdapter
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
            }
        }


        binding.addCareer.setOnTouchListener{view,event->
            if(event.action == MotionEvent.ACTION_UP){
                showAddCareer()
                //초기화 코드 추가하기
                viewModel.getClearData()
            }
            false
        }


    }

    override fun onResume() {
        super.onResume()
        resumeAdapter = ResumeAdapter(emptyList())
        binding.recycler.adapter = resumeAdapter
        viewModel.resumeData.observe(viewLifecycleOwner, Observer {newData:List<ResumeSummary>->
            resumeAdapter.updateList(newData)
            binding.carrerSum.text = viewModel.getCareerTotal()
        })
    }
    private fun showAddCareer() {
        val newFrament = CareerAddFragment()
        newFrament.show(parentFragmentManager,"careerAdd")
    }

}