package app.futured.kmptemplate.feature.ui.second

import app.futured.kmptemplate.feature.navigation.home.HomeNavigationEntry
import com.arkivanov.decompose.value.MutableValue

interface SecondScreen : HomeNavigationEntry {
    val viewState: MutableValue<SecondViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}
