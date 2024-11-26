package app.futured.kmptemplate.android.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.futured.kmptemplate.feature_v3.ui.profileScreen.ProfileScreen
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.kmpStringResource

@Composable
fun ProfileScreenUi(
    screen: ProfileScreen,
    modifier: Modifier = Modifier,
) {
    Content(modifier = modifier, actions = screen.actions)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    actions: ProfileScreen.Actions,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(kmpStringResource(MR.strings.profile_screen_title)) },
                windowInsets = WindowInsets.navigationBars,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Profile")
            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = actions::onLogout) {
                Text(kmpStringResource(MR.strings.generic_sign_out))
            }
        }
    }
}
