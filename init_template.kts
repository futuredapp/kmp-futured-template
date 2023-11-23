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

//// APP

val templatePackageName = "app.futured.kmptemplate"
val (appName, packageName) = getAppNameAndPackage()
renamePackagesInAndroidApp(packageName)
renamePackagesInShared(packageName = packageName)
renameTextInPath(pathText = "gradle/libs.versions.toml", oldText = templatePackageName, newText = packageName)
renameTextInPath(pathText = "build.gradle.kts", oldText = templatePackageName, newText = packageName)
renameTextInPath(pathText = "settings.gradle.kts", oldText = "KMP_Futured_template", newText = appName)

// IOS 
updateFastfileEnvVariables(filePath = "iosApp/fastlane/Fastfile", varName = "APP_IDENTIFIER", newValue = packageName)
updateFastfileEnvVariables(filePath = "iosApp/fastlane/Fastfile", varName = "APP_NAME", newValue = appName)
updateFastfileEnvVariables(filePath = "iosApp/fastlane/Fastfile", varName = "APP_SCHEME", newValue = appName)
updatePbxprojValues(filePath = "iosApp/iosApp.xcodeproj/project.pbxproj", oldValue = "orgIdentifier.iosApp", newValue = packageName)
updatePbxprojValues(filePath = "iosApp/iosApp.xcodeproj/project.pbxproj", oldValue = "orgIdentifier.iosApp.iosAppTests", newValue = "${packageName}.${appName}Test")
updatePbxprojValues(filePath = "iosApp/iosApp.xcodeproj/project.pbxproj", oldValue = "orgIdentifier.iosApp.iosAppUITests", newValue = "${packageName}.${appName}UITest")
replaceTextInAllFilesInDirectory(dirPath = "iosApp", oldText = "iosApp", newText = appName)
renameInDirectory(dirPath = "iosApp/iosAppTests", oldText = "iosApp", newText = appName)
renameInDirectory(dirPath = "iosApp/iosAppUITests", oldText = "iosApp", newText = appName)
renameInDirectory(dirPath = "iosApp/iosApp.xcodeproj/xcshareddata/xcschemes", oldText = "iosApp", newText = appName)
renameInDirectory(dirPath = "iosApp", oldText = "iosApp", newText = appName)

//// END APP

// region functions

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

fun replaceTextInAllFilesInDirectory(dirPath: String, oldText: String, newText: String) {
    val dir = File(dirPath)
    if (dir.exists()) {
        dir.walk().filter { it.isFile }.forEach { file ->
            renameTextInPath(file.path, oldText, newText)
        }
    } else {
        error("Directory does not exist: $dirPath")
    }
}

fun renamePackagesInAndroidApp(packageName: String) {
    // move androidApp to new package
    val baseDir = "androidApp/src/main/kotlin"
    renamePackageNameInDirectory(baseDir, templatePackageName.replace(".", "/"), packageName.replace(".", "/"))

    // rename package and imports
    renameTextInPath("androidApp/build.gradle.kts", templatePackageName, packageName)
    renameTextInPath("androidApp/proguard-rules.pro", templatePackageName, packageName)

    renameTextInFilePath(baseDir, templatePackageName, packageName)

    // buildSrc
    val buildSrcBaseDir = "buildSrc/src/main/kotlin"
    renamePackageNameInDirectory(buildSrcBaseDir, templatePackageName.replace(".", "/"), packageName.replace(".", "/"))
    renameTextInFilePath(buildSrcBaseDir, templatePackageName, packageName)
}

fun renamePackageNameInDirectory(dir: String, oldPackageNamePath: String, newPackageNamePath: String) {
    val oldPackageNameSeparated = oldPackageNamePath.split("/")
    val newPackageNameSeparated = newPackageNamePath.split("/")

    var newPath = dir
    oldPackageNameSeparated.forEachIndexed { index, name ->
        val toBeRenamed = "$newPath/$name"
        newPath = "$newPath/${newPackageNameSeparated[index]}"
        renameDirectory(toBeRenamed, newPath)
    }
}

fun renameDirectory(from: String, to: String) {
    if (!File(from).renameTo(File(to))) {
        println("Can't rename directory $from")
        return
    }
}

fun renamePackagesInShared(packageName: String) {
    val packagePath = packageName.replace('.', '/')
    val oldPackagePath = templatePackageName.replace('.', '/')

    val targets = listOf(
        "androidMain",
        "commonMain",
        "iosMain",
    )
    val modules = listOf(
        "app",
        "feature",
        "network/graphql",
        "network/rest",
        "persistence",
        "platform",
        "util",
    )
    modules.forEach { moduleName ->
        targets.forEach { targetName ->
            val baseDir = "shared/$moduleName/src/$targetName"
            if (File(baseDir).exists()) {
                renamePackageNameInDirectory(
                    dir = "$baseDir/kotlin",
                    oldPackageNamePath = oldPackagePath,
                    newPackageNamePath = packagePath,
                )
                renameTextInFilePath(
                    path = "$baseDir/kotlin",
                    oldText = templatePackageName,
                    newText = packageName,
                )
            }
        }

        renameTextInPath("shared/$moduleName/build.gradle.kts", templatePackageName, packageName)
    }
}

fun renameTextInFilePath(path: String, oldText: String, newText: String) {
    val f = File(path)
    f.walk().forEach {
        if (it.isDirectory.not()) {
            renameTextInPath(it.path, oldText, newText)
        }
    }
}

fun renameTextInPath(pathText: String, oldText: String, newText: String) {
    val path: Path = Paths.get(pathText)
    val charset = StandardCharsets.UTF_8

    var content = String(Files.readAllBytes(path), charset)
    content = content.replace(oldText.toRegex(), newText)
    Files.write(path, content.toByteArray(charset))
}

fun getAppNameAndPackage(): Pair<String, String> {
    print("Project name: ")
    val appName: String = readlnOrNull() ?: error("You need to enter name")

    print("Package name (e.g. com.example.test): ")
    val packageName = readlnOrNull() ?: error("You need to enter package name")

    if (packageName.count { it == '.' } != 2) {
        error("You did not enter package name correctly")
    }

    return appName to packageName
}
