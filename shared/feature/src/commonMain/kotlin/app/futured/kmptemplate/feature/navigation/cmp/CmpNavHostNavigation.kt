package app.futured.kmptemplate.feature.navigation.cmp

import app.futured.kmptemplate.feature.ui.formScreen.FormComponent
import app.futured.kmptemplate.feature.ui.formScreen.FormScreenNavigation
import app.futured.kmptemplate.feature.ui.interopCheck.InteropCheckComponent
import app.futured.kmptemplate.feature.ui.interopCheck.InteropCheckScreenNavigation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push

internal interface CmpNavHostNavigation :
    FormScreenNavigation, InteropCheckScreenNavigation {
    val navigator: StackNavigation<CmpConfig>
}

internal class CmpNavigator : CmpNavHostNavigation {
    override val navigator = StackNavigation<CmpConfig>()

    override fun FormComponent.pop() { navigator.pop() }
    override fun FormComponent.navigateToInteropCheck() {navigator.push(CmpConfig.InteropCheck)}
    override fun InteropCheckComponent.pop() { navigator.pop()}
}
