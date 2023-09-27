package com.example.nongglenonggle.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.MainActivity
import com.example.nongglenonggle.R
import com.example.nongglenonggle.viewModel.login.LoginViewModel
import com.example.nongglenonggle.databinding.ActivityLoginBinding
import com.example.nongglenonggle.view.signup.SignupActivity
import com.example.nongglenonggle.view.worker.home.WorkerMainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private val viewModel : LoginViewModel by viewModels()
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login)
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
            viewModel.viewModelScope.launch {
                val loginResult = withContext(Dispatchers.IO){
                    viewModel.loginWithEmailAndPassword(email,passwordText.text.toString())
                }
                Log.e("dsds","sdfsdf")
                //로그인 성공시
                if(loginResult){
                    //로그인 성공하고 farmer회원일때
                    if(viewModel.isFarmer.value==true)
                    {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        Log.e("dsds","성공")
                    }
                    //로그인 성공하고 구인자 회원일때
                    else
                    {
                        val intent = Intent(this@LoginActivity, WorkerMainActivity::class.java)
                        startActivity(intent)
                    }
                }
                //로그인 실패시
                else
                {
                    //Log.e("dsds","실패")
                    //임시적으로
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        val signup = binding.signupTxt
        val intent = Intent(this, SignupActivity::class.java)
        signup.setOnClickListener{startActivity(intent)}
    }
}