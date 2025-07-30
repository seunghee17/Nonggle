package com.capstone.nongglenonggle.app

//navigation을 구현하다보면 제일 난감한게 어디 경로로 가는지 확실히 파악할 수 없는것이다
//gorouter과 같은 방식으로 요즘엔 경로를 문자열로 지정하여 개발자가 명확히 어느경로로 갈수있는지
//파악 가능
sealed class Screens(val route: String) {
    object StartNonggleScreen: Screens("StartNonggle")
    object Signup: Screens("SignUp") {
        object SetType: Screens("SignUp/SetType")
        object Step1: Screens("SignUp/Step1")
        object Step2: Screens("SignUp/Step2")
        object Step3: Screens("SignUp/Step3") //구인자만 해당하는 step
    }
}