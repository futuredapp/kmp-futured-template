#!/bin/bash

//usr/bin/env echo '
/**** BOOTSTRAP kscript ****\'>/dev/null
command -v kscript >/dev/null 2>&1 || curl -L "https://git.io/fpF1K" | bash 1>&2
exec kscript $0 "$@"
\*** IMPORTANT: Any code including imports and annotations must come after this line ***/

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.exists
import kotlin.io.path.extension
import kotlin.io.path.isRegularFile

val templatePackageName = "app.futured.kmptemplate"
val templatePackagePath: Path = Path.of("app/futured/kmptemplate")

val (appName, appPackageName) = readInput()
val appPackagePath = Path.of(appPackageName.replace('.', '/'))

// region Android + KMP + Gradle

renameGradleSubproject("buildSrc", appPackageName)
renameGradleSubproject("baselineprofile", appPackageName)
renameGradleSubproject("androidApp", appPackageName)
renameGradleSubproject("shared/app", appPackageName)
renameGradleSubproject("shared/feature", appPackageName)
renameGradleSubproject("shared/network/graphql", appPackageName)
renameGradleSubproject("shared/network/rest", appPackageName)
renameGradleSubproject("shared/persistence", appPackageName)
renameGradleSubproject("shared/platform", appPackageName)
renameGradleSubproject("shared/resources", appPackageName)

findAndReplaceInFileTree(
    parent = Path.of("shared/arkitekt-decompose"),
    search = templatePackageName,
    replaceWith = appPackageName,
)

findAndReplaceInFileTree(
    parent = Path.of("shared/arkitekt-cr-usecases"),
    search = templatePackageName,
    replaceWith = appPackageName,
)

findAndReplaceInFileTree(
    parent = Path.of(".github"),
    search = templatePackageName,
    replaceWith = appPackageName,
)

findAndReplaceInFile(
    file = File("gradle/libs.versions.toml"),
    search = templatePackageName,
    replaceWith = appPackageName,
)

findAndReplaceInFile(
    file = File("build.gradle.kts"),
    search = templatePackageName,
    replaceWith = appPackageName,
)

findAndReplaceInFile(
    file = File("settings.gradle.kts"),
    search = "KMP_Futured_template",
    replaceWith = appName,
)

// endregion

// region XCode project

updateFastfileEnvVariables(
    file = File("iosApp/fastlane/Fastfile"),
    varName = "APP_IDENTIFIER",
    newValue = appPackageName,
)
updateFastfileEnvVariables(
    file = File("iosApp/fastlane/Fastfile"),
    varName = "APP_NAME",
    newValue = appName,
)
updateFastfileEnvVariables(
    file = File("iosApp/fastlane/Fastfile"),
    varName = "APP_SCHEME",
    newValue = appName,
)
findAndReplaceInFile(
    file = File("iosApp/iosApp.xcodeproj/project.pbxproj"),
    search = "orgIdentifier.iosApp",
    replaceWith = appPackageName,
)
findAndReplaceInFile(
    file = File("iosApp/iosApp.xcodeproj/project.pbxproj"),
    search = "orgIdentifier.iosApp.iosAppTests",
    replaceWith = "${appPackageName}.${appName}Test",
)
findAndReplaceInFile(
    file = File("iosApp/iosApp.xcodeproj/project.pbxproj"),
    search = "orgIdentifier.iosApp.iosAppUITests",
    replaceWith = "${appPackageName}.${appName}UITest",
)

findAndReplaceInFileTree(parent = Path.of("iosApp"), search = "iosApp", replaceWith = appName)

moveFileTree(
    parent = Path.of("iosApp"),
    fromPath = Path.of("iosApp"),
    toPath = Path.of(appName),
)
moveFileTree(
    parent = Path.of("iosApp"),
    fromPath = Path.of("iosApp.xcodeproj"),
    toPath = Path.of("$appName.xcodeproj"),
)
Files.move(
    File("iosApp/iosAppTests/iosAppTests.swift").toPath(),
    File("iosApp/iosAppTests/${appName}Tests.swift").toPath(),
)
moveFileTree(
    parent = Path.of("iosApp"),
    fromPath = Path.of("iosAppTests"),
    toPath = Path.of("${appName}Tests"),
)
Files.move(
    File("iosApp/iosAppUITests/iosAppUITestsLaunchTests.swift").toPath(),
    File("iosApp/iosAppUITests/${appName}UITestsLaunchTests.swift").toPath(),
)
moveFileTree(
    parent = Path.of("iosApp"),
    fromPath = Path.of("iosAppUITests"),
    toPath = Path.of("${appName}UITests"),
)

// endregion

// region Repo

File("LICENSE").delete()
File("init_template.kts").delete()

if (confirmBuild()) {
    ProcessBuilder("./gradlew", "assembleKMPDebugXCFramework").inheritIO().start().waitFor()
}

// endregion

// region Functions

fun renameGradleSubproject(subproject: String, appPackage: String) {
    val sourceSets = listOf(
        Path.of("$subproject/src/main/kotlin"),
        Path.of("$subproject/src/commonMain/kotlin"),
        Path.of("$subproject/src/commonTest/kotlin"),
        Path.of("$subproject/src/androidMain/kotlin"),
        Path.of("$subproject/src/iosMain/kotlin"),
    )

    for (sourceSet in sourceSets) {
        moveFileTree(
            parent = sourceSet,
            fromPath = templatePackagePath,
            toPath = appPackagePath,
        )
    }

    findAndReplaceInFileTree(
        parent = Path.of(subproject),
        search = templatePackageName,
        replaceWith = appPackage,
        extensionFilter = { extension -> extension == "kt" || extension == "kts" },
    )
}

fun findAndReplaceInFileTree(
    parent: Path,
    search: String,
    replaceWith: String,
    extensionFilter: (extension: String) -> Boolean = { true },
) {
    if (parent.exists().not()) {
        return
    }
    Files.walk(parent)
        .filter { path -> path.isRegularFile() }
        .filter { path -> extensionFilter(path.extension) }
        .map { it.toFile() }
        .forEach { file ->
            with(file) { writeText(readText(Charsets.UTF_8).replace(search, replaceWith)) }
        }
}

fun findAndReplaceInFile(
    file: File,
    search: String,
    replaceWith: String,
) {
    if (file.exists().not()) {
        return
    }
    with(file) { writeText(readText(Charsets.UTF_8).replace(search, replaceWith)) }
}

fun moveFileTree(parent: Path, fromPath: Path, toPath: Path) {
    if (parent.exists().not()) {
        return
    }
    val sourcePath = parent.resolve(fromPath)
    val targetPath = parent.resolve(toPath)

    Files.walk(parent.resolve(fromPath))
        .filter { path -> path.isRegularFile() }
        .forEach { source ->
            val target = targetPath.resolve(sourcePath.relativize(source))
            Files.createDirectories(target.parent)
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING)
        }
}

fun updateFastfileEnvVariables(file: File, varName: String, newValue: String) {
    if (!file.exists()) {
        println("File does not exist: $file")
        return
    }

    var content = file.readText()

    // Look for the pattern ENV['VAR_NAME'] = 'var_value' and change 'var_value'
    val envVarPattern = "ENV\\['$varName'] = '(.*)'".toRegex()
    if (envVarPattern.containsMatchIn(content)) {
        // The variable is found in the file - replace the old value with the new value
        content = content.replace(envVarPattern, "ENV['$varName'] = '$newValue'")
        file.writeText(content)
    } else {
        println("Variable $varName was not found in Fastfile.")
    }
}

fun readInput(): Pair<String, String> {
    print("Project name: ")
    val appName: String = readlnOrNull()
        ?.takeIf { it.isNotBlank() }
        ?.replace(" ", "_")
        ?: error("Invalid name entered")

    print("Package name (e.g. com.example.test): ")
    val packageName = readlnOrNull()
        ?.takeIf { it.isNotBlank() }
        ?: error("Invalid package name")

    return Pair(appName, packageName)
}

fun confirmBuild(): Boolean {
    print("The script will now build Swift Package for the first time? [y/n]\n\n (This is optional, but needs to be done manually for the first time using './gradlew assembleKMPDebugXCFramework' in order for XCode build to work)")
    return readlnOrNull()?.trim()?.lowercase() == "y"
}

fun removeLicense() {
    File("LICENSE").delete()
}

// endregion
