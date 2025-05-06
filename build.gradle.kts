import app.futured.kmptemplate.gradle.task.LintCheckTask
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
    // Apply plugins at the top level but don't apply to this project
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
}

tasks.register<LintCheckTask>("lintCheck")
tasks.register<ReportMergeTask>("detektReportMerge") {
    output.set(rootProject.layout.buildDirectory.file("reports/detekt/merged.xml"))
}

subprojects {
    if (tasks.findByName("testClasses") == null) {
        tasks.register("testClasses") {
            println(
                "This is a dummy testClasses task to satisfy Android Studio `Make Project` action. " +
                    "See https://kotlinlang.slack.com/archives/C3PQML5NU/p1696987572655649 for more info.",
            )
        }
    }
}
