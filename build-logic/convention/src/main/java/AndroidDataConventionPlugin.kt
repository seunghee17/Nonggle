import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidDataConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("example.android.library")
            }
        }
    }
}