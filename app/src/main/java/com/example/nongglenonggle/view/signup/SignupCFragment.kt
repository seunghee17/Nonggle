package com.example.nongglenonggle.view.signup

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.R
import com.example.nongglenonggle.base.BaseFragment
import com.example.nongglenonggle.databinding.FragmentSignupCBinding
import com.example.nongglenonggle.view.login.LoginActivity
import com.example.nongglenonggle.view.worker.home.WorkerMainActivity
import com.example.nongglenonggle.viewModel.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupCFragment : BaseFragment<FragmentSignupCBinding>(R.layout.fragment_signup_c) {
    private val viewModel: SignupViewModel by activityViewModels()

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
        nextbtn.setOnClickListener{
            viewModel.cstepActive.observe(viewLifecycleOwner){ isactive->
                if(viewModel.isHire.value ==true)
                {
                    moveToNext()
                }
                else{
                    EndSignupWorker()
                }
            }
        }

    }
    private fun checkConditions(){
        if((binding.checkbox1.isChecked&&binding.checkbox2.isChecked&&binding.checkbox3.isChecked) || binding.allcheckbox.isChecked){
            viewModel._cstepActive.value = true
        }
        else{
            viewModel._cstepActive.value = false
        }
    }
    fun moveToNext()
    {
        replaceFragment(SignupDFragment(), R.id.signup_fragmentcontainer)
    }
    fun EndSignupWorker()
    {
        //구직자 홈화면으로
        val intent= Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }

}