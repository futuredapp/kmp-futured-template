package app.futured.kmptemplate.feature.tool

import dev.icerock.moko.resources.desc.StringDesc

/**
 * Represents an error that can be displayed in UI.
 *
 * @param title shared error title string resource to display in UI.
 * @param message optional shared error message string resource to display in UI.
 */
data class UiError(
    val title: StringDesc,
    val message: StringDesc? = null,
)
