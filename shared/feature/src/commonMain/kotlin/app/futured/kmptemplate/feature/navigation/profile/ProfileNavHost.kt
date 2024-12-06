package app.futured.kmptemplate.feature.navigation.profile

import app.futured.kmptemplate.feature.navigation.deepLink.DeepLinkDestination
import app.futured.kmptemplate.feature.ui.profileScreen.ProfileScreen
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreen
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreenArgs
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface ProfileNavHost : BackHandlerOwner {

    val stack: StateFlow<ChildStack<ProfileConfig, ProfileChild>>
    val actions: Actions

    interface Actions {
        fun pop()
        fun navigate(newStack: List<Child<ProfileConfig, ProfileChild>>)
    }
}

@Serializable
sealed interface ProfileConfig {

    @Serializable
    data object Profile : ProfileConfig

    @Serializable
    data class Third(val args: ThirdScreenArgs) : ProfileConfig
}

sealed interface ProfileChild {
    data class Profile(val screen: ProfileScreen) : ProfileChild
    data class Third(val screen: ThirdScreen) : ProfileChild
}
