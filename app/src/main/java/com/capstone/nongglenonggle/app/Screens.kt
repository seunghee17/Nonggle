package com.capstone.nongglenonggle.app

//Jetpack Compose Navigation을 사용할 때 경로가 문자열로 관리되기 때문에
//어디로 이동하는지 명확하지 않은 경우가 많음
//이를 방지하기 위해 컴파일 타임에 유효한 경로를 사용할 수 있도록 모든 경로 타입을 정의하여 구조화함
sealed class Screens(val route: String) {
    object StartNonggleScreen: Screens("StartNonggle")
    object Signup: Screens("SignUp") {
        object SetType: Screens("SignUp/SetType")
        object Step2: Screens("SignUp/Step2")
        object Step3: Screens("SignUp/Step3") //구인자만 해당하는 step
        object AddressSearchWebView: Screens("SignUp/AddressSearch")
    }

    object WorkerResumeWriting: Screens("WorkerResume") {
        object ResumeStep1: Screens("WorkerResume/Setp1")
        object ResumeStep2: Screens("WorkerResume/Setp2")
        object ResumeStep3: Screens("WorkerResume/Setp3")
        object ResumeStep4: Screens("WorkerResume/Setp4")
        object ResumeComplete: Screens("WorkerResume/Complete")
    }
}