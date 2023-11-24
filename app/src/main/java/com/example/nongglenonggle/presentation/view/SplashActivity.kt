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


class SplashActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val firestore = FirebaseFirestore.getInstance()

    //coroutinescope 객체 생성
    //dispatcher.main 사용해서 코루틴이 메인(ui) 쓰레드에서 실행되도록 설정한다
    val activityScope= CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //코루틴을 시작하는 블록
        activityScope.launch {
            delay(3000)
            val intent = Intent(this@SplashActivity, com.example.nongglenonggle.presentation.view.FirstActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

//    override fun onStart() {
//        super.onStart()
//        if(user != null){
//            val uid = user.uid
//            detectUserType(uid)
//        }
//    }



    //액티비티가 일시정지 상태로 전환될때 호출되는 생명주기 함수
    //현재 실행중인 코루틴을 취소
    //액티비티 일시정지되는 동안 불필요한 코루틴 실행 방지
    override fun onPause()
    {
        activityScope.cancel()
        super.onPause()
    }

    fun detectUserType(uid:String){
        val userFarmer = firestore.collection("Farmer").document(uid)
        val userWorker = firestore.collection("Worker").document(uid)
        userFarmer.get().addOnSuccessListener {documentSnapshot->
            if(documentSnapshot.exists()){
                //구인자 유형
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
        userWorker.get().addOnSuccessListener { document->
            if(document.exists()){
                //구직자 유형
                startActivity(Intent(this,WorkerMainActivity::class.java))
                finish()
            }
        }
    }

}