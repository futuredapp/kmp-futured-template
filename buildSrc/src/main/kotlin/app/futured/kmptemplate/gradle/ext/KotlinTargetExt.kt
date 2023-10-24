package app.futured.kmptemplate.gradle.ext

import org.jetbrains.kotlin.gradle.dsl.KotlinTargetContainerWithNativeShortcuts
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

/**
 * Configures supported native iOS targets all at once.
 */
fun KotlinTargetContainerWithNativeShortcuts.iosTargets(
    targets: List<KotlinNativeTarget> = listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ),
    configure: (KotlinNativeTarget) -> Unit = {},
) {
    targets.forEach { configure(it) }
}
