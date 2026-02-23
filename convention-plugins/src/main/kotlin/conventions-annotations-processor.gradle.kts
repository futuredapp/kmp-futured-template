import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.support.uppercaseFirstChar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

// As of now, we cannot use Gradle version catalogs with Precompiled Script plugins: https://github.com/gradle/gradle/issues/15383
plugins {
    id("com.google.devtools.ksp")
}

// Allow configuring which processor to use (or both)
abstract class AnnotationsExtension {

    /**
     * Configures Koin annotations if enabled.
     */
    var useKoin: Boolean = false

    /**
     * Configures Component factory generation if enabled
     */
    var useComponentFactory: Boolean = false

    /**
     * Configures Android build variant KSP task dependencies for each build variant provided
     */
    var androidBuildTypes: List<String> = emptyList()
}

val extension = extensions.create("annotations", AnnotationsExtension::class)

// Configure KSP after the extension has been configured
afterEvaluate {
    if (extension.useKoin) {
        ksp {
            // enable compile time check
            arg("KOIN_CONFIG_CHECK", "false")
            // disable default module generation
            arg("KOIN_DEFAULT_MODULE", "false")
        }
    }

    // https://github.com/gradle/gradle/issues/15383
    val libs = the<LibrariesForLibs>()

    // Add the appropriate dependencies based on configuration
    dependencies {
        if (extension.useKoin) {
            add("kspCommonMainMetadata", libs.koin.ksp.compiler)
            add("kspAndroid", libs.koin.ksp.compiler)
            add("kspIosArm64", libs.koin.ksp.compiler)
            add("kspIosSimulatorArm64", libs.koin.ksp.compiler)
        }

        // Enable source generation by KSP to commonMain only
        if (extension.useComponentFactory) {
            add("kspCommonMainMetadata", project(":shared:arkitekt-decompose:processor"))
        }
    }
}

// WORKAROUND: ADD this dependsOn("kspCommonMainKotlinMetadata") instead of above dependencies
tasks.withType<KotlinCompilationTask<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

// Manual wiring of task dependencies needed for build variant-dependent KSP tasks
tasks.named { taskName ->
    val taskNames = extension.androidBuildTypes.map { "ksp${it.uppercaseFirstChar()}KotlinAndroid" }
    taskName in taskNames
}.configureEach {
    mustRunAfter("kspCommonMainKotlinMetadata")
}
