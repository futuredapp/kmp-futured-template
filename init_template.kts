#!/bin/bash

//usr/bin/env echo '
/**** BOOTSTRAP kscript ****\'>/dev/null
command -v kscript >/dev/null 2>&1 || curl -L "https://git.io/fpF1K" | bash 1>&2
exec kscript $0 "$@"
\*** IMPORTANT: Any code including imports and annotations must come after this line ***/

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile

//// APP

val templatePackageName = "app.futured.kmptemplate"
val templatePackagePath: Path = Path.of("app/futured/kmptemplate")

val (appName, appPackageName, frameworkName) = getNamesOfAppAndPackageAndFramework()

renameGradleSubproject("buildSrc", appPackageName)
renameGradleSubproject("baselineprofile", appPackageName)
renameGradleSubproject("androidApp", appPackageName)
renameGradleSubproject("shared/app", appPackageName)
renameGradleSubproject("shared/feature", appPackageName)
renameGradleSubproject("shared/network", appPackageName)
renameGradleSubproject("shared/persistence", appPackageName)
renameGradleSubproject("shared/platform", appPackageName)
renameGradleSubproject("shared/resources", appPackageName)

findAndReplaceInFileTree(
    parent = Path.of("shared/arkitekt-decompose"),
    search = templatePackageName,
    replaceWith = appPackageName
)

findAndReplaceInFileTree(
    parent = Path.of("shared/arkitekt-cr-usecases"),
    search = templatePackageName,
    replaceWith = appPackageName
)

findAndReplaceInFileTree(
    parent = Path.of(".github"),
    search = templatePackageName,
    replaceWith = appPackageName
)

findAndReplaceInFile(
    file = File("gradle/libs.versions.toml"),
    search = templatePackageName,
    replaceWith = appPackageName
)

findAndReplaceInFile(
    file = File("build.gradle.kts"),
    search = templatePackageName,
    replaceWith = appPackageName
)

findAndReplaceInFile(
    file = File("settings.gradle.kts"),
    search = "KMP_Futured_template",
    replaceWith = appName
)

fun renameGradleSubproject(subproject: String, appPackage: String) {
    val sourceSets = listOf(
        Path.of("$subproject/src/main/kotlin"),
        Path.of("$subproject/src/commonMain/kotlin"),
        Path.of("$subproject/src/commonTest/kotlin"),
        Path.of("$subproject/src/androidMain/kotlin"),
        Path.of("$subproject/src/iosMain/kotlin")
    )

    for (sourceSet in sourceSets) {
        moveFileTree(
            parent = sourceSet,
            fromPath = templatePackagePath,
            toPath = Path.of(appPackage.replace('.', '/'))
        )
    }

    findAndReplaceInFileTree(
        parent = Path.of(subproject),
        search = templatePackageName,
        replaceWith = appPackage
    )
}

fun findAndReplaceInFileTree(parent: Path, search: String, replaceWith: String) {
    if (parent.exists().not()) {
        return
    }
    Files.walk(parent)
        .filter { path -> path.isRegularFile() }
        .map { it.toFile() }
        .forEach { file ->
            with(file) { writeText(readText(Charsets.UTF_8).replace(search, replaceWith)) }
        }
}

fun findAndReplaceInFile(file: File, search: String, replaceWith: String) {
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
            println("moved file: $source -> $target")
        }
}

// IOS
updateFastfileEnvVariables(filePath = "iosApp/fastlane/Fastfile", varName = "APP_IDENTIFIER", newValue = appPackageName)
updateFastfileEnvVariables(filePath = "iosApp/fastlane/Fastfile", varName = "APP_NAME", newValue = appName)
updateFastfileEnvVariables(filePath = "iosApp/fastlane/Fastfile", varName = "APP_SCHEME", newValue = appName)
updatePbxprojValues(filePath = "iosApp/iosApp.xcodeproj/project.pbxproj", oldValue = "orgIdentifier.iosApp", newValue = appPackageName)
updatePbxprojValues(filePath = "iosApp/iosApp.xcodeproj/project.pbxproj", oldValue = "orgIdentifier.iosApp.iosAppTests", newValue = "${appPackageName}.${appName}Test")
updatePbxprojValues(filePath = "iosApp/iosApp.xcodeproj/project.pbxproj", oldValue = "orgIdentifier.iosApp.iosAppUITests", newValue = "${appPackageName}.${appName}UITest")
renameTextInPath(pathText = "buildSrc/src/main/kotlin/${appPackageName.replace(".", "/")}/gradle/configuration/ProjectSettings.kt", oldText = "shared", newText = "$frameworkName")
replaceTextInSwiftFiles(dirPath = "iosApp", oldText = "import shared", newText = "import ${frameworkName}")
replaceTextInSwiftFiles(dirPath = "iosApp", oldText = "extension shared", newText =  "extension ${frameworkName}")
replaceTextInXConfigFiles(dirPath = "iosApp", oldText = "shared", newText = "${frameworkName}")
findAndReplaceInFileTree(parent = Path.of("iosApp"), search = "iosApp", replaceWith = appName)
renameInDirectory(dirPath = "iosApp/iosAppTests", oldText = "iosApp", newText = appName)
renameInDirectory(dirPath = "iosApp/iosAppUITests", oldText = "iosApp", newText = appName)
renameInDirectory(dirPath = "iosApp/iosApp.xcodeproj/xcshareddata/xcschemes", oldText = "iosApp", newText = appName)
renameInDirectory(dirPath = "iosApp", oldText = "iosApp", newText = appName)

//// END APP

// region functions

fun replaceTextInXConfigFiles(dirPath: String, oldText: String, newText: String) {
    File(dirPath).walk()
        .filter { it.isFile && it.extension == "xcconfig" }
        .forEach { file -> renameTextInPath(file.path, oldText, newText) }
}

fun replaceTextInSwiftFiles(dirPath: String, oldText: String, newText: String) {
    File(dirPath).walk()
        .filter { it.isFile && it.extension == "swift" }
        .forEach { file -> renameTextInPath(file.path, oldText, newText) }
}

fun updatePbxprojValues(filePath: String, oldValue: String, newValue: String) {
    val file = File(filePath)
    if (!file.exists()) {
        println("File does not exist: $filePath")
        return
    }
    var content = file.readText()
    content = content.replace(oldValue, newValue)
    file.writeText(content)
}

fun updateFastfileEnvVariables(filePath: String, varName: String, newValue: String) {
    val file = File(filePath)
    if (!file.exists()) {
        println("File does not exist: $filePath")
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


fun renameInDirectory(dirPath: String, oldText: String, newText: String) {
    val dir = File(dirPath)
    if (!dir.exists()) {
        error("Directory does not exist: $dirPath")
        return
    }

    // exclude root directory from walk results
    dir.walk().drop(1).forEach { file ->
        if (file.name.contains(oldText)) {
            val oldName = file.name
            val newName = file.name.replace(oldText, newText)
            renameDirectory(from = "$dirPath/$oldName", to = "$dirPath/$newName")
        }
    }
}

fun renameDirectory(from: String, to: String) {
    File(from).renameTo(File(to))
}

fun renameTextInPath(pathText: String, oldText: String, newText: String) {
    val path: Path = Paths.get(pathText)
    val charset = StandardCharsets.UTF_8

    var content = String(Files.readAllBytes(path), charset)
    content = content.replace(oldText.toRegex(), newText)
    Files.write(path, content.toByteArray(charset))
}

fun getNamesOfAppAndPackageAndFramework(): Triple<String, String, String> {
    print("Project name: ")
    val appName: String = readLine()
        ?.takeIf { it.isNotBlank() }
        ?.replace(" ", "_")
        ?: error("You need to enter name")

    print("Package name (e.g. com.example.test): ")
    val packageName = readLine()
        ?.takeIf { it.isNotBlank() }
        ?: error("You need to enter package name")

    print("Framework name: (default 'shared'): ")
    val frameworkName = readLine()
        ?.takeIf { it.isNotBlank() }
        ?.replace(" ", "_")
        ?: "shared"

    if (packageName.count { it == '.' } != 2) {
        error("You did not enter package name correctly")
    }

    return Triple(appName, packageName, frameworkName)
}
