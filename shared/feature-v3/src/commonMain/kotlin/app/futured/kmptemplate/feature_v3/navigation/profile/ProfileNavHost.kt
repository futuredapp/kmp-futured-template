package app.futured.kmptemplate.feature_v3.navigation.profile

import app.futured.kmptemplate.feature_v3.ui.profileScreen.ProfileScreen
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
}

sealed interface ProfileChild {
    data class Profile(val screen: ProfileScreen) : ProfileChild
}
