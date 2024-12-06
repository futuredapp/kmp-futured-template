package app.futured.kmptemplate.feature.navigation.home

import app.futured.kmptemplate.feature.ui.firstScreen.FirstComponent
import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreenNavigation
import app.futured.kmptemplate.feature.ui.secondScreen.SecondComponent
import app.futured.kmptemplate.feature.ui.secondScreen.SecondScreenNavigation
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdComponent
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreenArgs
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreenNavigation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import org.koin.core.annotation.Single

internal interface HomeNavigation : FirstScreenNavigation, SecondScreenNavigation, ThirdScreenNavigation {
    val navigator: StackNavigation<HomeConfig>
}

@Single
internal class HomeNavigator : HomeNavigation {
    override val navigator = StackNavigation<HomeConfig>()

    override fun FirstComponent.navigateToSecond() =
        navigator.pushNew(HomeConfig.Second)

    override fun SecondComponent.pop() = navigator.pop()


    override fun SecondComponent.navigateToThird(id: String) =
        navigator.pushNew(HomeConfig.Third(ThirdScreenArgs(id)))

    override fun ThirdComponent.pop() = navigator.pop()
}
