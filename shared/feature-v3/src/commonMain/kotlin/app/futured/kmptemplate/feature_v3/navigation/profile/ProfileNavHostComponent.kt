package app.futured.kmptemplate.feature_v3.navigation.profile

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.kmptemplate.feature_v3.ui.base.AppComponent
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentContext
import app.futured.kmptemplate.feature_v3.ui.base.AppComponentFactory
import app.futured.kmptemplate.feature_v3.ui.profileScreen.ProfileComponent
import app.futured.kmptemplate.feature_v3.ui.profileScreen.ProfileScreenNavigation
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
    @InjectedParam private val initialStack: List<ProfileConfig>,
) : AppComponent<Unit, Nothing>(componentContext, Unit), ProfileNavHost {

    private val stackNavigator = StackNavigation<ProfileConfig>()
    override val stack: StateFlow<ChildStack<ProfileConfig, ProfileChild>> = childStack(
        source = stackNavigator,
        serializer = ProfileConfig.serializer(),
        initialStack = { initialStack },
        handleBackButton = true,
        childFactory = { config, childCtx ->
            when (config) {
                ProfileConfig.Profile -> ProfileChild.Profile(
                    AppComponentFactory.createComponent<ProfileComponent>(
                        childContext = childCtx,
                        navigation = ProfileScreenNavigation(
                            pop = {},
                        ),
                    ),
                )
            }
        },
    ).asStateFlow(componentCoroutineScope)

    override val actions: ProfileNavHost.Actions = object : ProfileNavHost.Actions {
        override fun pop() = stackNavigator.pop()
        override fun navigate(newStack: List<Child<ProfileConfig, ProfileChild>>) = stackNavigator.navigate {
            newStack.map { it.configuration }
        }
    }
}
