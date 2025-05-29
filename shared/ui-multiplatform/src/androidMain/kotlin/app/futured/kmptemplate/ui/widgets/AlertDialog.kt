package app.futured.kmptemplate.ui.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import app.futured.kmptemplate.feature.data.model.AlertDialogUi
import app.futured.kmptemplate.resources.localized

@Composable
actual fun Alert(
    alertDialogUi: AlertDialogUi,
) {
    AlertDialog(
        dismissButton = alertDialogUi.dismissButton?.let {
            {
                Button(onClick = {alertDialogUi.onDismiss()}) { Text(it.localized()) }
            }
        },
        confirmButton = alertDialogUi.positiveButton.let {
            {
                Button(onClick = {alertDialogUi.onDismiss()}) { Text(it.localized()) }
            }
        },
        onDismissRequest = {alertDialogUi.onDismiss()},
        title = alertDialogUi.title?.let { { Text(it.localized()) } },
        text = alertDialogUi.message.let { { Text(it.localized()) } },
    )
}
