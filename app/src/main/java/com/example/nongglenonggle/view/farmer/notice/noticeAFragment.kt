package com.example.nongglenonggle.view.farmer.notice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentNoticeABinding
import com.example.nongglenonggle.viewModel.farmer.notice.FarmerNoticeViewModel

class noticeAFragment : Fragment() {
    private var _binding : FragmentNoticeABinding? = null
    private val viewModel: FarmerNoticeViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoticeABinding.inflate(inflater,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text1 = binding.namebox.text.toString()
        val text2 = binding.phnumbox.text.toString()
        val text3 = binding.detailAddress.text.toString()
        Log.e("tag", "${viewModel.toNext.value}")


        binding.namebox.setOnFocusChangeListener{
            view,isfocus ->
            if(isfocus)
            {
                viewModel.changeToActive1()
                binding.namebox.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.xcircle, 0)
            }
            else
            {
                binding.namebox.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            }
        }
        binding.namebox.setOnTouchListener { view, event ->
            if(event.action == MotionEvent.ACTION_UP){
                val drawableRightXStart = binding.namebox.right - binding.namebox.totalPaddingRight
                if(event.rawX >= drawableRightXStart)
                {
                    binding.namebox.setText("")
                    return@setOnTouchListener true
                }
            }
            false
        }
        binding.phnumbox.setOnFocusChangeListener { view, isfocus ->

            if(isfocus)
            {
                viewModel.changeToActive2()
                binding.namebox.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.xcircle, 0)
            }
            else
            {
                binding.namebox.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            }

        }
        binding.detailAddress.setOnFocusChangeListener { view, isfocus ->
            if(isfocus)
            {
                viewModel.changeToActive3()
                viewModel.updateNext()
                binding.namebox.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.xcircle, 0)
            }
            else
            {
                binding.namebox.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
            }
        }

        binding.nextBtn.setOnClickListener{
            viewModel.switchTab.value = true
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}