package app.futured.kmptemplate.feature.navigation.cmp

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.ui.base.AppComponent
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.formScreen.FormComponentFactory
import app.futured.kmptemplate.feature.ui.interopCheck.InteropCheckComponentFactory
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@GenerateFactory
@Factory
internal class CmpNavHostComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam private val initialStack: List<CmpConfig>,
) : AppComponent<Unit, Nothing>(componentContext, Unit),
    CmpNavHost {

    private val CmpNavigator: CmpNavHostNavigation = CmpNavigator()

    override val stack: StateFlow<ChildStack<CmpConfig, CmpChild>> = childStack(
        source = CmpNavigator.navigator,
        serializer = CmpConfig.serializer(),
        initialStack = { initialStack },
        key = "CmpStack",
        handleBackButton = true,
        childFactory = { config, childCtx ->
            when (config) {
                CmpConfig.Form -> CmpChild.Form(FormComponentFactory.createComponent(childCtx, CmpNavigator))
                CmpConfig.InteropCheck -> CmpChild.InteropCheck(InteropCheckComponentFactory.createComponent(childCtx, CmpNavigator))
            }
        },
    ).asStateFlow()

    override val actions: CmpNavHost.Actions = object : CmpNavHost.Actions {
        override fun navigate(newStack: List<Child<CmpConfig, CmpChild>>) =
            CmpNavigator.navigator.navigate { newStack.map { it.configuration } }

        override fun pop() = CmpNavigator.navigator.pop()
    }
}
