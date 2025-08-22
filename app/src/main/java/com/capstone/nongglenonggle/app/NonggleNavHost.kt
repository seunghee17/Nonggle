package com.capstone.nongglenonggle.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.capstone.nongglenonggle.presentation.view.signup.nav_controller.SignupNavGraph
import com.capstone.nongglenonggle.presentation.view.worker.resume.WorkerResumeGraph

@Composable
fun NonggleNavHost() {
    //컴포저블 가장 높은 위치에 만들어야한다 참조하는 컴포저블이 실행할 수 있도록 부모계층
    val navHostController = rememberNavController()
    NavHost(
        navController = navHostController,
        //startDestination은 navhost에 직접적 자식의 screen을 지정해주어야함 object안에 내부 object이면 안됨
        startDestination = Screens.Signup.route
    ) {
        SignupNavGraph(navHostController = navHostController)
        WorkerResumeGraph(navHostController = navHostController)
    }
}