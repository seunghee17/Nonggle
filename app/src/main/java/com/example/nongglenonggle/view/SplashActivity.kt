package com.example.nongglenonggle.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nongglenonggle.R
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    //coroutinescope 객체 생성
    //dispatcher.main 사용해서 코루틴이 메인(ui) 쓰레드에서 실행되도록 설정한다
    val activityScope= CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //코루틴을 시작하는 블록
        activityScope.launch {
            delay(3000)
            val intent = Intent(this@SplashActivity, FirstActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //액티비티가 일시정지 상태로 전환될때 호출되는 생명주기 함수
    //현재 실행중인 코루틴을 취소
    //액티비티 일시정지되는 동안 불필요한 코루틴 실행 방지
    override fun onPause()
    {
        activityScope.cancel()
        super.onPause()
    }
}