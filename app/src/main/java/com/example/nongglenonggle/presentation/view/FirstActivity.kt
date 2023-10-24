package com.example.nongglenonggle.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.nongglenonggle.R
import com.example.nongglenonggle.databinding.ActivityFirstBinding
import com.example.nongglenonggle.presentation.view.farmer.home.MainActivity
import com.example.nongglenonggle.presentation.view.signup.SignupActivity
import com.example.nongglenonggle.presentation.view.login.LoginActivity
import com.example.nongglenonggle.presentation.view.worker.home.WorkerMainActivity
import com.example.nongglenonggle.presentation.viewModel.FirstViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

class FirstActivity : AppCompatActivity() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val activityScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private val viewModel: FirstViewModel by viewModels()
    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val loginBtn = binding.loginBtn
        val signupBtn = binding.signupBtn


        val intent = Intent(this, LoginActivity::class.java)
        val intent2 = Intent(this, SignupActivity::class.java)

        activityScope.launch {
            loginBtn.setOnClickListener {
                startActivity(intent) }

            signupBtn.setOnTouchListener{
                    view,event->
                when(event.action){
                    MotionEvent.ACTION_DOWN ->{
                        view.isSelected = !view.isSelected
                        viewModel.setButtonSelected()
                        updateTextColor()
                        startActivity(intent2)
                    }
                }
                false
            }
        }

        }
    override fun onStart() {
        super.onStart()
        val current = auth.currentUser
        if(current != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateTextColor()
    {
        val activeText = if(viewModel.isSelected) R.color.m1 else
            R.color.g2
        binding.signupBtn.setTextColor(ContextCompat.getColor(this,activeText))
    }

    override fun onPause()
    {
        activityScope.cancel()
        super.onPause()
    }

    }
