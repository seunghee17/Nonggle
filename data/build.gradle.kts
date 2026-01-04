plugins {
    alias(libs.plugins.nongglenonggle.android.data)
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}