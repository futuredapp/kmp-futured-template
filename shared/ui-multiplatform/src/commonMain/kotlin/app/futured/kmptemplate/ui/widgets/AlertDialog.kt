package app.futured.kmptemplate.ui.widgets

import androidx.compose.runtime.Composable
import app.futured.kmptemplate.feature.data.model.AlertDialogUi

@Composable
expect fun Alert(
    alertDialogUi: AlertDialogUi,
)
