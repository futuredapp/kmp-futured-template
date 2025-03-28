package app.futured.kmptemplate.feature.navigation.profile

import app.futured.kmptemplate.feature.ui.profileScreen.ProfileScreen
import app.futured.kmptemplate.feature.ui.profileScreen.ProfileScreenNavigation
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdComponent
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreenArgs
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreenNavigation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push

internal interface ProfileNavHostNavigation :
    ProfileScreenNavigation,
    ThirdScreenNavigation {
    val stackNavigator: StackNavigation<ProfileConfig>
}

internal class ProfileNavHostNavigator(private val onNavigateToLogin: () -> Unit) : ProfileNavHostNavigation {
    override val stackNavigator = StackNavigation<ProfileConfig>()

    override fun ProfileScreen.navigateToLogin() = onNavigateToLogin()

    override fun ProfileScreen.navigateToThird(id: String) {
        stackNavigator.push(ProfileConfig.Third(ThirdScreenArgs(id)))
    }

    override fun ThirdComponent.pop() {
        stackNavigator.pop()
    }
}
