package app.futured.kmptemplate.feature.ui.third

import app.futured.kmptemplate.feature.navigation.home.HomeNavigationEntry
import com.arkivanov.decompose.value.MutableValue

interface ThirdScreen: HomeNavigationEntry {
    val viewState: MutableValue<ThirdViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}
