package com.example.nongglenonggle.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.nongglenonggle.MainActivity
import com.example.nongglenonggle.R
import com.example.nongglenonggle.base.BaseActivity
import com.example.nongglenonggle.viewModel.login.LoginViewModel
import com.example.nongglenonggle.databinding.ActivityLoginBinding
import com.example.nongglenonggle.view.signup.SignupActivity
import com.example.nongglenonggle.view.worker.home.WorkerMainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                //viewModel.getUserType()
                viewModel.isFarmer.observe(this, Observer { isFarmer->
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    Log.e("applesdf","구인자")
                    //Log.e("applesdf","${viewModel.isFarmer.value}")
                    Log.e("applesdf","${viewModel.UserUID}")
                })
                viewModel.isWorker.observe(this, Observer { isWorker->
                    val intent = Intent(this,WorkerMainActivity::class.java)
                    startActivity(intent)
                    Log.e("apple","구직자")
                    Log.e("apple","${viewModel.isWorker.value}")
                })
            }
        }

        val signup = binding.signupTxt
        val intent = Intent(this, SignupActivity::class.java)
        signup.setOnClickListener{startActivity(intent)}
    }
}