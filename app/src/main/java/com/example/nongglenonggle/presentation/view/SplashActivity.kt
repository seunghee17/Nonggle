package com.example.nongglenonggle.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nongglenonggle.R
import com.example.nongglenonggle.presentation.view.farmer.home.MainActivity
import com.example.nongglenonggle.presentation.view.worker.home.WorkerMainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class SplashActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val firestore = FirebaseFirestore.getInstance()

    val activityScope= CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        activityScope.launch {
            delay(3000)
            if(user?.uid != null){
                val uid = user.uid
                val type = detectUserType(uid)
                    if(type == "Farmer"){
                        val intent = Intent(this@SplashActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        val intent = Intent(this@SplashActivity,WorkerMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

            }
            else{
                val intent = Intent(this@SplashActivity,FirstActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onPause()
    {
        activityScope.cancel()
        super.onPause()
    }

    suspend fun detectUserType(uid:String):String= coroutineScope{
        val userFarmer = firestore.collection("Farmer").document(uid).get().await()
        val userWorker = firestore.collection("Worker").document(uid).get().await()
        if(userFarmer.exists()){
            return@coroutineScope "Farmer"
        }
        else{
            return@coroutineScope "Worker"
        }
    }

}