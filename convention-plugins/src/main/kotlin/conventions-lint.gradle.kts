import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

/*
This is a Precompiled Script Plugin that unifies lint
configuration across multiple Gradle modules in the project.

https://docs.gradle.org/current/userguide/custom_plugins.html#sec:precompiled_plugins
 */

// As of now, we cannot use Gradle version catalogs with Precompiled Script plugins: https://github.com/gradle/gradle/issues/15383
plugins {
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

// Ktlint
project.configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    ignoreFailures.set(true)
    android.set(true)
    outputToConsole.set(true)
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
    }
    filter {
        exclude("**/generated/**")
    }
}

// Detekt
project.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
    ignoreFailures = false
    source.setFrom(files(projectDir))
    config.setFrom(files("$rootDir/config/detekt.yml"))
    buildUponDefaultConfig = true
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
    reports {
        sarif.required.set(false)
        md.required.set(false)
        txt.required.set(true)
        xml.required.set(true)
    }

    finalizedBy(rootProject.tasks.getByName("detektReportMerge"))
}

/**
 * Configure task [runKtlintCheckOverCommonMainSourceSet] to run After KSP generation task
 * Setup this only if project has configured ksp and has [kspCommonMainKotlinMetadata] in it's tasks
 */
tasks.matching { it.name == "runKtlintCheckOverCommonMainSourceSet" }.configureEach {
    if (project.tasks.findByName("kspCommonMainKotlinMetadata") != null) {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

tasks.matching { it.name == "runKtlintCheckOverCommonMainSourceSet" }.configureEach {
    if (project.tasks.findByName("generateMRcommonMain") != null) {
        dependsOn("generateMRcommonMain")
    }
}

rootProject.tasks.named("detektReportMerge", ReportMergeTask::class.java) {
    input.from(tasks.withType<Detekt>().map { it.xmlReportFile })
}
