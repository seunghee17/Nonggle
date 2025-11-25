//// Dependencies.kt
//object Versions {
//
//    // AndroidX
//    const val APP_COMPAT = "1.4.1"
//    const val MATERIAL = "1.5.0"
//    const val CONSTRAINT_LAYOUT = "2.1.3"
//
//    // KTX
//    const val CORE = "1.7.0"
//
//    // TEST
//    const val JUNIT = "1.1.3"
//
//    // Android Test
//    const val ESPRESSO_CORE = "3.4.0"
//}
//
//object Libraries {
//
//    //android ui
//    private val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
//    private val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
//    private val constraintLayout =
//        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
//
//    val appLibraries = arrayListOf<String>().apply {
//        add(kotlinStdLib)
//        add(coreKtx)
//        add(appcompat)
//        add(constraintLayout)
//    }
//}
//
////util functions for adding the different type dependencies from build.gradle file
//fun DependencyHandler.kapt(list: List<String>) {
//    list.forEach { dependency ->
//        add("kapt", dependency)
//    }
//}
//
//fun DependencyHandler.implementation(list: List<String>) {
//    list.forEach { dependency ->
//        add("implementation", dependency)
//    }
//}
//
//fun DependencyHandler.androidTestImplementation(list: List<String>) {
//    list.forEach { dependency ->
//        add("androidTestImplementation", dependency)
//    }
//}
//
//fun DependencyHandler.testImplementation(list: List<String>) {
//    list.forEach { dependency ->
//        add("testImplementation", dependency)
//    }
//}