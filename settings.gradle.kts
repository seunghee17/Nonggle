pluginManagement {
    include("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "nongglenonggle"
include (":app")
include (":build-logic:convention")
include(":domain")
include(":core")
include(":feature")
include(":data")
