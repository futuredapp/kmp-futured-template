package app.futured.kmptemplate.feature.ui.first

import app.futured.kmptemplate.feature.navigation.home.HomeNavigationEntry
import com.arkivanov.decompose.value.MutableValue

interface FirstScreen : HomeNavigationEntry {
    val viewState: MutableValue<FirstViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}
