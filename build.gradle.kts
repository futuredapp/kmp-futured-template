import app.futured.kmptemplate.gradle.task.CleanTask
import app.futured.kmptemplate.gradle.task.LintCheckTask
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

tasks.register<CleanTask>("clean")
tasks.register<LintCheckTask>("lintCheck")
tasks.register<ReportMergeTask>("detektReportMerge") {
    output.set(rootProject.layout.buildDirectory.file("reports/detekt/merged.xml"))
}
