package app.futured.kmptemplate.feature.data.model

import dev.icerock.moko.resources.desc.StringDesc

data class AlertDialogUi(
    val positiveButton: StringDesc,
    val message: StringDesc,
    val title: StringDesc? = null,
    val dismissButton: StringDesc? = null,
    val onPositiveClick: () -> Unit = {},
    val onDismiss: () -> Unit = {},
)
