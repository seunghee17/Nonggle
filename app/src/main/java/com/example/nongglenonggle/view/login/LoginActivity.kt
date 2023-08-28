package com.example.nongglenonggle.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.nongglenonggle.R
import com.example.nongglenonggle.viewModel.LoginViewModel
import com.example.nongglenonggle.databinding.ActivityLoginBinding
import com.example.nongglenonggle.view.farmer.signup.SignupActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel : LoginViewModel by viewModels()
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //비밀번호 칸 포커스 여부시 활성화
        val passwordText = binding.passwordTxt
        passwordText.setOnFocusChangeListener{
            _, hasFocus -> viewModel.setEditTextFocused(hasFocus)
        }

        //아이디칸 포커스 활성화
        val idTxt = binding.idTxt
        idTxt.setOnFocusChangeListener{
            _, hasFocus -> viewModel.setIDEditTextFocused(hasFocus)
        }

        val signup = binding.signupTxt
        val intent = Intent(this, SignupActivity::class.java)
        signup.setOnClickListener{startActivity(intent)}
    }
}