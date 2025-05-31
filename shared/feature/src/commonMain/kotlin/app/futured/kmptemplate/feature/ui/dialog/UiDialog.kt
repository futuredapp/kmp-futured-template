package app.futured.kmptemplate.feature.ui.dialog

import app.futured.kmptemplate.feature.tool.UiError
import app.futured.kmptemplate.resources.MR
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

data class UiDialog(
    val title: StringDesc,
    val text: StringDesc?,
    val primaryButton: PrimaryButton,
    val dismissButton: DismissButton? = null,
    val androidProperties: AndroidProperties,
) {
    interface DialogButton {
        val title: StringDesc
        val action: () -> Unit
    }

    data class PrimaryButton(override val title: StringDesc, override val action: () -> Unit) : DialogButton

    data class DismissButton(override val title: StringDesc, override val action: () -> Unit) : DialogButton

    data class AndroidProperties(
        val dismissOnBackPress: Boolean = true,
        val dismissOnCLickOutside: Boolean = true,
        val dismissRequest: () -> Unit = {},
    )
}

private fun defaultAndroidProperties(dismissRequest: () -> Unit) = UiDialog.AndroidProperties(
    dismissOnBackPress = true,
    dismissOnCLickOutside = true,
    dismissRequest = dismissRequest,
)

/**
 * Creates new Dialog with title, text and a single confirm button to dismiss the dialog.
 */
fun infoDialog(
    title: StringDesc,
    text: StringDesc?,
    onConfirm: () -> Unit,
    button: StringDesc = MR.strings.ok.desc(),
): UiDialog = UiDialog(
    title = title,
    text = text,
    primaryButton = UiDialog.PrimaryButton(
        title = button,
        action = onConfirm,
    ),
    dismissButton = null,
    androidProperties = defaultAndroidProperties(onConfirm),
)

/**
 * Creates a new [UiDialog] which shows provided [UiError].
 */
fun errorDialog(
    error: UiError,
    onConfirm: () -> Unit,
    button: StringDesc = MR.strings.ok.desc(),
): UiDialog = UiDialog(
    title = error.title,
    text = error.message,
    primaryButton = UiDialog.PrimaryButton(
        title = button,
        action = onConfirm,
    ),
    dismissButton = null,
    androidProperties = defaultAndroidProperties(onConfirm),
)

/**
 * Creates a new [UiDialog] with title, text and two buttons - confirm and dismiss.
 */
fun actionDialog(
    title: StringDesc,
    text: StringDesc,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmButton: StringDesc,
    negativeButton: StringDesc,
): UiDialog = UiDialog(
    title = title,
    text = text,
    primaryButton = UiDialog.PrimaryButton(
        title = confirmButton,
        action = onConfirm,
    ),
    dismissButton = UiDialog.DismissButton(
        title = negativeButton,
        action = onDismiss,
    ),
    androidProperties = defaultAndroidProperties(onDismiss),
)
