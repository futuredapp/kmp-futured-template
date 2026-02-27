@file:Suppress("FunctionNaming")

package app.futured.kmptemplate.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreen
import app.futured.kmptemplate.feature.ui.loginScreen.LoginScreen
import app.futured.kmptemplate.feature.ui.picker.PickerScreen
import app.futured.kmptemplate.feature.ui.profileScreen.ProfileScreen
import app.futured.kmptemplate.feature.ui.secondScreen.SecondScreen
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreen
import app.futured.kmptemplate.ui.screen.FirstScreenUi
import app.futured.kmptemplate.ui.screen.LoginScreenUi
import app.futured.kmptemplate.ui.screen.PickerScreenUi
import app.futured.kmptemplate.ui.screen.ProfileScreenUi
import app.futured.kmptemplate.ui.screen.SecondScreenUi
import app.futured.kmptemplate.ui.screen.ThirdScreenUi

fun FirstUiController(screen: FirstScreen) = ComposeUIViewController {
    AppTheme { FirstScreenUi(screen = screen, modifier = Modifier.fillMaxSize()) }
}

fun SecondUiController(screen: SecondScreen) = ComposeUIViewController {
    AppTheme { SecondScreenUi(screen = screen, modifier = Modifier.fillMaxSize()) }
}

fun ThirdUiController(screen: ThirdScreen) = ComposeUIViewController {
    AppTheme { ThirdScreenUi(screen = screen, modifier = Modifier.fillMaxSize()) }
}

fun LoginUiController(screen: LoginScreen) = ComposeUIViewController {
    AppTheme { LoginScreenUi(screen = screen, modifier = Modifier.fillMaxSize()) }
}

fun ProfileUiController(screen: ProfileScreen) = ComposeUIViewController {
    AppTheme { ProfileScreenUi(screen = screen, modifier = Modifier.fillMaxSize()) }
}

fun PickerUiController(screen: PickerScreen) = ComposeUIViewController {
    AppTheme { PickerScreenUi(pickerScreen = screen, modifier = Modifier.fillMaxSize()) }
}
