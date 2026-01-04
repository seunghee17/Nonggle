plugins {
    alias(libs.plugins.nongglenonggle.android.library.compose)
}

android {
    namespace = "com.example.designsystem"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}