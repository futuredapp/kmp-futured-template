package app.futured.kmptemplate.gradle.task

import app.futured.kmptemplate.gradle.configuration.ProjectSettings
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskAction

open class CleanTask : Delete() {

    init {
        group = ProjectSettings.Gradle.TaskGroup
    }

    @TaskAction
    fun cleanProject() {
        project.rootProject.buildDir.deleteRecursively()
        project.subprojects.forEach {
            delete(it.buildDir)
        }
    }
}
