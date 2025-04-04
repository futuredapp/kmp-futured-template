package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.navigation.signedIn.SignedInNavHostComponent
import app.futured.kmptemplate.feature.navigation.signedIn.SignedInNavHostNavigation
import app.futured.kmptemplate.feature.ui.loginScreen.LoginComponent
import app.futured.kmptemplate.feature.ui.loginScreen.LoginScreenNavigation
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate

internal interface RootNavHostNavigation :
    LoginScreenNavigation,
    SignedInNavHostNavigation {
    val slotNavigator: SlotNavigation<RootConfig>
}

internal class RootNavHostNavigator : RootNavHostNavigation {
    override val slotNavigator: SlotNavigation<RootConfig> = SlotNavigation()

    override fun LoginComponent.navigateToSignedIn() {
        slotNavigator.activate(RootConfig.SignedIn())
    }

    override fun SignedInNavHostComponent.navigateToLogin() {
        slotNavigator.activate(RootConfig.Login)
    }
}
