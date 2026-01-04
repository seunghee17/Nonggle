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
            id = "example.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "example.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibrary") {
            id = "example.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "example.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidData") {
            id = "example.android.data"
            implementationClass = "AndroidDataConventionPlugin"
        }
    }
}
