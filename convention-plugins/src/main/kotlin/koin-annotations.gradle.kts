import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.gradle.accessors.dm.LibrariesForLibs

// As of now, we cannot use Gradle version catalogs with Precompiled Script plugins: https://github.com/gradle/gradle/issues/15383
plugins {
    id("com.google.devtools.ksp")
}

ksp {
    // enable compile time check
    arg("KOIN_CONFIG_CHECK","false")
    // disable default module generation
    arg("KOIN_DEFAULT_MODULE","false")

    // setup for component processor
    arg("appComponentContext", "app.futured.kmptemplate.util.arch.AppComponentContext")
    arg("viewModelExt", "app.futured.kmptemplate.util.arch.viewModel")
}

// https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()
// Enable source generation by KSP to commonMain only
dependencies {
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
    add("kspCommonMainMetadata", project(":shared:util:component-processor"))
    // DO NOT add bellow dependencies
//    add("kspAndroid", Deps.Koin.kspCompiler)
//    add("kspIosX64", Deps.Koin.kspCompiler)
//    add("kspIosArm64", Deps.Koin.kspCompiler)
//    add("kspIosSimulatorArm64", Deps.Koin.kspCompiler)
}

// WORKAROUND: ADD this dependsOn("kspCommonMainKotlinMetadata") instead of above dependencies
tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
