package com.capstone.nongglenonggle.app.navigation

//Jetpack Compose Navigation을 사용할 때 경로가 문자열로 관리되기 때문에
//어디로 이동하는지 명확하지 않은 경우가 많음
//이를 방지하기 위해 컴파일 타임에 유효한 경로를 사용할 수 있도록 모든 경로 타입을 정의하여 구조화함
sealed class Screens(val route: String) {
    object Splash: Screens("Splash") {
        object SplashScreen: Screens("Splash/Main")
    }
    object Login: Screens("Login") {
        object LoginScreen: Screens("Login/Main")
    }
    object Signup: Screens("SignUp") {
        object SetType: Screens("SignUp/SetType")
        object Step2: Screens("SignUp/Step2")
        object Step3: Screens("SignUp/Step3") //구인자만 해당하는 step
        object AddressSearch: Screens("SignUp/AddressSearch")
    }

    object WorkerResumeWriting: Screens("WorkerResume") {
        object ResumeTabScreen: Screens("WorkerResume/main")
        object ResumeComplete: Screens("WorkerResume/Complete")
    }

    object FarmerNoticeWriting: Screens("FarmerNotice") {
        object NoticeOnBoardingScreen: Screens("FarmerNotice/onboarding")
        object NoticeTabScreen: Screens("FarmerNotice/main")
        object NoticeFinalScreen: Screens("FarmerNotice/final")
    }
}