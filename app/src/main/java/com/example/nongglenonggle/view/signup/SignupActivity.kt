package com.example.nongglenonggle.view.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.nongglenonggle.R
import com.example.nongglenonggle.base.BaseActivity
import com.example.nongglenonggle.viewModel.signup.SignupViewModel
import com.example.nongglenonggle.databinding.ActivitySignupBinding
import com.example.nongglenonggle.view.FirstActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
@AndroidEntryPoint
class SignupActivity : BaseActivity<ActivitySignupBinding>(R.layout.activity_signup) {
    val activityScope= CoroutineScope(Dispatchers.Main)
    private val viewModel : SignupViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

         //이전버튼 정의
        binding.backBtn.setOnClickListener{
            val intent = Intent(this, FirstActivity::class.java)
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