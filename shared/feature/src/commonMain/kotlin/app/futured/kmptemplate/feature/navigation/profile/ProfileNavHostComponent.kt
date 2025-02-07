package app.futured.kmptemplate.feature.navigation.profile

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.kmptemplate.feature.ui.base.AppComponent
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.profileScreen.ProfileComponentFactory
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdComponentFactory
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class ProfileNavHostComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam toLogin: () -> Unit,
    @InjectedParam private val initialStack: List<ProfileConfig>,
) : AppComponent<Unit, Nothing>(componentContext, Unit), ProfileNavHost {

    private val navigator: ProfileNavHostNavigation = ProfileNavHostNavigator(toLogin)

    override val stack: StateFlow<ChildStack<ProfileConfig, ProfileChild>> = childStack(
        source = navigator.stackNavigator,
        serializer = ProfileConfig.serializer(),
        initialStack = { initialStack },
        handleBackButton = true,
        childFactory = { config, childCtx ->
            when (config) {
                ProfileConfig.Profile -> ProfileChild.Profile(ProfileComponentFactory.createComponent(childCtx, navigator))
                is ProfileConfig.Third -> ProfileChild.Third(ThirdComponentFactory.createComponent(childCtx, navigator, config.args))
            }
        },
    ).asStateFlow()

    override val actions: ProfileNavHost.Actions = object : ProfileNavHost.Actions {
        override fun navigate(newStack: List<Child<ProfileConfig, ProfileChild>>) =
            navigator.stackNavigator.navigate { newStack.map { it.configuration } }

        override fun pop() = navigator.stackNavigator.pop()
    }
}
