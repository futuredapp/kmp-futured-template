package app.futured.kmptemplate.android.ui.components.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import app.futured.kmptemplate.android.tools.compose.ScreenPreviews
import app.futured.kmptemplate.android.ui.components.Showcase
import app.futured.kmptemplate.feature.ui.dialog.UiDialog
import app.futured.kmptemplate.resources.localized
import app.futured.mdevcamp.android.ui.theme.MDevTheme
import dev.icerock.moko.resources.desc.desc


@Composable
fun MDevDialog(
    uiDialog: UiDialog,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        tonalElevation = 0.dp,
        containerColor = MDevTheme.colors.background,
        textContentColor = MDevTheme.colors.white60,
        titleContentColor = MDevTheme.colors.white,
        title = {
            Text(
                text = uiDialog.title.localized(),
                style = MDevTheme.typography.title4,
            )
        },
        text = uiDialog.text?.let {
            {
                Text(text = it.localized(), style = MDevTheme.typography.textNormalRegular)
            }
        },
        onDismissRequest = uiDialog.androidProperties.dismissRequest,
        confirmButton = {
            Button(onClick = uiDialog.primaryButton.action) {
                Text(
                    text = uiDialog.primaryButton.title.localized().uppercase(),
                    style = MDevTheme.typography.textSmallRegular,
                )
            }
        },
        dismissButton = uiDialog.dismissButton?.let { dismissButton ->
            {
                TextButton(onClick = dismissButton.action) {
                    Text(
                        text = dismissButton.title.localized().uppercase(),
                        style = MDevTheme.typography.textSmallBold,
                    )
                }
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = uiDialog.androidProperties.dismissOnBackPress,
            dismissOnClickOutside = uiDialog.androidProperties.dismissOnCLickOutside,
        ),
        modifier = modifier,
    )
}

@ScreenPreviews
@Composable
private fun MDevDialogPreview() {
    val dialog = UiDialog(
        title = "Dialog title".desc(),
        text = "Dialog text".desc(),
        primaryButton = UiDialog.PrimaryButton(
            title = "Ok".desc(),
            action = {},
        ),
        dismissButton = UiDialog.DismissButton(
            title = "Dismiss".desc(),
            action = {},
        ),
        androidProperties = UiDialog.AndroidProperties(),
    )
    Showcase {
        Box(Modifier.fillMaxSize()) {
            MDevDialog(uiDialog = dialog)
        }
    }
}

