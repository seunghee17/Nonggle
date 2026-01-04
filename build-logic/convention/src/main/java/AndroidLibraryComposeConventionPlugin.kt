import com.android.build.gradle.LibraryExtension
import com.example.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("nongglenonggle.android.library") //AndroidLibrary 위한 플러그인 적용
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}