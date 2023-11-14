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
renameTextInPath("settings.gradle.kts", "KMP_Futured_template", appName)

//// END APP

// region functions
fun renamePackagesInAndroidApp(packageName: String) {
    // move androidApp to new package
    val baseDir = "androidApp/src/main/kotlin"
    renamePackageNameInDirectory(baseDir, templatePackageName.replace(".", "/"), packageName.replace(".", "/"))

    // rename package and imports
    renameTextInFilePath("androidApp/", templatePackageName, packageName)
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
    println("Enter app name: ")
    val appName: String = readlnOrNull() ?: error("You need to enter name")

    println("Enter app package name (e.g. com.example.test):")
    val packageName = readlnOrNull() ?: error("You need to enter package name")
    return appName to packageName
}
