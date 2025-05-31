package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.ui.welcomeScreen.WelcomeComponent
import app.futured.kmptemplate.feature.ui.welcomeScreen.WelcomeScreenNavigation
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate

internal interface RootNavHostNavigation :
    WelcomeScreenNavigation{
    val slotNavigator: SlotNavigation<RootConfig>
}

internal class RootNavHostNavigator : RootNavHostNavigation {
    override val slotNavigator: SlotNavigation<RootConfig> = SlotNavigation()

    override fun WelcomeComponent.navigateToHome() {
        slotNavigator.activate(RootConfig.Home())
    }
}
