plugins {
    `kotlin-dsl`
}

group = "com.nongglenonggle.build-logic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "capstone.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}
