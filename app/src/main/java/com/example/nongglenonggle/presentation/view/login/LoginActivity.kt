package com.example.nongglenonggle.presentation.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.nongglenonggle.presentation.view.farmer.home.MainActivity
import com.example.nongglenonggle.R
import com.example.nongglenonggle.presentation.base.BaseActivity
import com.example.nongglenonggle.presentation.viewModel.login.LoginViewModel
import com.example.nongglenonggle.databinding.ActivityLoginBinding
import com.example.nongglenonggle.presentation.view.signup.SignupActivity
import com.example.nongglenonggle.presentation.view.worker.home.WorkerMainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val loginbtn = binding.loginVerify

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

        loginbtn.setOnClickListener {
            val phnum = idTxt.text.toString()
            val email = "$phnum@example.com"
            viewModel.loginWithEmailAndPassword(email,passwordText.text.toString())
            if(viewModel.isLoginAvailable.value == true){
                viewModel.isFarmer.observe(this, Observer { isFarmer->
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                })
                viewModel.isWorker.observe(this, Observer { isWorker->
                    val intent = Intent(this,WorkerMainActivity::class.java)
                    startActivity(intent)
                })
            }
        }

        val signup = binding.signupTxt
        val intent = Intent(this, SignupActivity::class.java)
        signup.setOnClickListener{startActivity(intent)}
    }


}