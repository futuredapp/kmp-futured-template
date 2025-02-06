import gradle.kotlin.dsl.accessors._411868000f8e772c6f299e8a28e6e73d.ksp
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.kotlin.dsl.the

// As of now, we cannot use Gradle version catalogs with Precompiled Script plugins: https://github.com/gradle/gradle/issues/15383
plugins {
    id("com.google.devtools.ksp")
}

// https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()
// Enable source generation by KSP to commonMain only
dependencies {
    add("kspCommonMainMetadata", project(":shared:factory-generator:processor"))
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
