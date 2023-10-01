package com.example.nongglenonggle.presentation.view.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.nongglenonggle.R
import com.example.nongglenonggle.presentation.base.BaseActivity
import com.example.nongglenonggle.presentation.viewModel.signup.SignupViewModel
import com.example.nongglenonggle.databinding.ActivitySignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : BaseActivity<ActivitySignupBinding>(R.layout.activity_signup) {
    private val viewModel : SignupViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

         //이전버튼 정의
        binding.backBtn.setOnClickListener{
            val intent = Intent(this, com.example.nongglenonggle.presentation.view.FirstActivity::class.java)
            startActivity(intent)
        }


        if(savedInstanceState == null)
        {
            supportFragmentManager.beginTransaction()
                .replace(binding.signupFragmentcontainer.id,SignupAFragment())
                .addToBackStack(null)
                .commit()
        }

    }

}