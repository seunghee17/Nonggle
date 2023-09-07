package com.example.nongglenonggle.view.farmer.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.nongglenonggle.R
import com.example.nongglenonggle.viewModel.farmer.signup.SignupViewModel
import com.example.nongglenonggle.databinding.ActivitySignupBinding
import com.example.nongglenonggle.viewModel.farmer.signup.SignupDViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class SignupActivity : AppCompatActivity() {
    val activityScope= CoroutineScope(Dispatchers.Main)
    private val viewModel : SignupViewModel by viewModels()
    private lateinit var binding: ActivitySignupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_signup)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
         //이전버튼 정의
        val backBtn = binding.backBtn

        //viewmodel을 관찰해서 activity위에 띄운다
        viewModel.currentFragment.observe(this) {
            fragment->
            supportFragmentManager.beginTransaction()
                .replace(R.id.signup_fragmentcontainer,fragment)
                .addToBackStack(null) //이전fragment로 돌아갈 수 있다
                .commit()
        }

    }
//    fun updateAddressData(data:String){
//        val viewModelD = ViewModelProvider(this).get(SignupDViewModel::class.java)
//        viewModelD.updateData(data)
//    }
}