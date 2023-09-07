package com.example.nongglenonggle.view.farmer.signup

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.FragmentSignupCBinding
import com.example.nongglenonggle.viewModel.farmer.signup.SignupCViewModel
import com.example.nongglenonggle.viewModel.farmer.signup.SignupViewModel

class SignupCFragment : Fragment() {
    private lateinit var viewModel: SignupCViewModel
    private lateinit var binding: FragmentSignupCBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupCBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(SignupCViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

       return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val allcheckbox = binding.allcheckbox
        val checkbox1 = binding.checkbox1
        val checkbox2 = binding.checkbox2
        val checkbox3 = binding.checkbox3
        val checkbox4 = binding.checkbox4
        val checkbox5 = binding.checkbox5
        val detail1 = binding.detail1
        val detail2 = binding.detail2
        val detail3 = binding.detail3
        val detail4 = binding.detail4
        val nextbtn = binding.nextBtn

        nextbtn.setOnClickListener{
            if(nextbtn.background is ColorDrawable)
            {
                val currentColor = (nextbtn.background as ColorDrawable).color
                if(currentColor==resources.getColor(R.color.m1)){
                    viewModel.onNextbtnClick()
                }
            }
        }

        viewModel.navigateToframentD.observe(viewLifecycleOwner)
        {
            navigate->
            if(navigate)
            {
                viewModel.doneNavigate()
                val fragmentD = SignupDFragment()
                val signupViewModel: SignupViewModel by activityViewModels()
                signupViewModel.navigateTo(fragmentD)
            }
        }
        //전체 선택 컨트롤용
        allcheckbox.setOnClickListener{
            val isChecked = allcheckbox.isChecked
            checkbox1.isChecked = isChecked
            checkbox2.isChecked = isChecked
            checkbox3.isChecked = isChecked
            checkbox4.isChecked = isChecked
            checkbox5.isChecked = isChecked
            checkConditions()
        }

        detail1.setOnClickListener{
            val dialog1fragment = Dialog1Fragment()
            dialog1fragment.show(childFragmentManager, "dialog1")
        }
        detail2.setOnClickListener{
            val dialog4fragment = Dialog4Fragment()
            dialog4fragment.show(childFragmentManager, "dialog4")
        }
        detail3.setOnClickListener{
            val dialog3fragment = Dialog3Fragment()
            dialog3fragment.show(childFragmentManager, "dialog3")
        }
        detail4.setOnClickListener{
            val dialog2fragment = Dialog2Fragment()
            dialog2fragment.show(childFragmentManager, "dialog2")
        }
        checkbox1.setOnClickListener{
            checkConditions()
        }
        checkbox2.setOnClickListener{
            checkConditions()
        }
        checkbox3.setOnClickListener{
            checkConditions()
        }


    }
    private fun updateTypeBackground(isComplete:Boolean)
    {
        val drawables = if(isComplete) resources.getColor(R.color.m1) else resources.getColor(R.color.unactive)
        binding.nextBtn.setBackgroundColor(drawables)
    }
    private fun checkConditions(){
        if((binding.checkbox1.isChecked&&binding.checkbox2.isChecked&&binding.checkbox3.isChecked) || binding.allcheckbox.isChecked){
            viewModel.ToNextFragment()
            updateTypeBackground(viewModel.isComplete)
        }
    }

}