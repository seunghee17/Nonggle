package com.capstone.nongglenonggle.presentation.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.capstone.nongglenonggle.presentation.view.farmer.home.MainActivity
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.presentation.base.BaseActivity
import com.capstone.nongglenonggle.presentation.viewModel.login.LoginViewModel
import com.capstone.nongglenonggle.databinding.ActivityLoginBinding
import com.capstone.nongglenonggle.presentation.view.signup.SignupActivity
import com.capstone.nongglenonggle.presentation.view.worker.home.WorkerMainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel : LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setup()

        binding.loginVerify.setOnClickListener {
            val phnum = binding.idTxt.text.toString()
            val email = "$phnum@example.com"
            viewModel.loginWithEmailAndPassword(email,binding.passwordTxt.text.toString())
            if(viewModel.isLoginAvailable.value == true){
                viewModel.isFarmer.observe(this, Observer { isFarmer->
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                })
                viewModel.isWorker.observe(this, Observer { isWorker->
                    val intent = Intent(this,WorkerMainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                })
            }
        }

    }

    fun setup(){
        //비밀번호 칸 포커스 여부시 활성화
        binding.passwordTxt.setOnFocusChangeListener{
                _, hasFocus -> viewModel.setEditTextFocused(hasFocus)
        }

        //아이디칸 포커스 활성화
        binding.idTxt.setOnFocusChangeListener{
                _, hasFocus -> viewModel.setIDEditTextFocused(hasFocus)
        }

        binding.deleteBtnID.setOnClickListener{
            binding.idTxt.text.clear()
        }
        binding.deleteBtn.setOnClickListener{
            binding.passwordTxt.text.clear()
        }

        binding.signupTxt.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        }
    }

    fun googleLoginClick(view: View){
        Toast.makeText(this,"검수중입니다",Toast.LENGTH_SHORT).show()
    }
    fun kakaoLoginClick(view: View){
        Toast.makeText(this,"검수중입니다",Toast.LENGTH_SHORT).show()
    }




}