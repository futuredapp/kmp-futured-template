import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

// As of now, we cannot use Gradle version catalogs with Precompiled Script plugins: https://github.com/gradle/gradle/issues/15383
plugins {
    id("com.google.devtools.ksp")
}

// Allow configuring which processor to use (or both)
abstract class AnnotationsExtension {
    var useKoin: Boolean = false
    var useComponentFactory: Boolean = false
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
        }

        // Enable source generation by KSP to commonMain only
        if (extension.useComponentFactory) {
            add("kspCommonMainMetadata", project(":shared:arkitekt-decompose:processor"))
            // DO NOT add bellow dependencies
            // add("kspAndroid", Deps.Koin.kspCompiler)
            // add("kspIosX64", Deps.Koin.kspCompiler)
            // add("kspIosArm64", Deps.Koin.kspCompiler)
            // add("kspIosSimulatorArm64", Deps.Koin.kspCompiler)
        }
    }
}

// WORKAROUND: ADD this dependsOn("kspCommonMainKotlinMetadata") instead of above dependencies
tasks.withType<KotlinCompilationTask<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        if (tasks.findByName("kspCommonMainKotlinMetadata") != null) {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
}
