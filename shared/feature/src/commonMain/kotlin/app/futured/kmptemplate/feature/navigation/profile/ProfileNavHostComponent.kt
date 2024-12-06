package app.futured.kmptemplate.feature.navigation.profile

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.kmptemplate.feature.ui.base.AppComponent
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.AppComponentFactory
import app.futured.kmptemplate.feature.ui.profileScreen.ProfileComponent
import app.futured.kmptemplate.feature.ui.profileScreen.ProfileScreenNavigation
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdComponent
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreenNavigation
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
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

    private val navigator = ProfileNavHostNavigator(toLogin)

    override val stack: StateFlow<ChildStack<ProfileConfig, ProfileChild>> = childStack(
        source = navigator.stackNavigator,
        serializer = ProfileConfig.serializer(),
        initialStack = { initialStack },
        handleBackButton = true,
        childFactory = { config, childCtx ->
            when (config) {
                ProfileConfig.Profile -> ProfileChild.Profile(
                    AppComponentFactory.createComponent<ProfileComponent, ProfileScreenNavigation>(
                        childContext = childCtx,
                        navigation = navigator,
                    ),
                )

                is ProfileConfig.Third -> ProfileChild.Third(
                    AppComponentFactory.createComponent<ThirdComponent, ThirdScreenNavigation>(
                        childContext = childCtx,
                        navigation = navigator,
                        config.args
                    )
                )
            }
        },
    ).asStateFlow(componentCoroutineScope)

    override val actions: ProfileNavHost.Actions = object : ProfileNavHost.Actions {
        override fun pop() = navigator.stackNavigator.pop()
        override fun navigate(newStack: List<Child<ProfileConfig, ProfileChild>>) = navigator.stackNavigator.navigate {
            newStack.map { it.configuration }
        }
    }
}
