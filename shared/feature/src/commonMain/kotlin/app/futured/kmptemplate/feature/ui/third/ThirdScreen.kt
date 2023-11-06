package app.futured.kmptemplate.feature.ui.third

import app.futured.kmptemplate.util.arch.Component
import com.arkivanov.decompose.value.MutableValue

interface ThirdScreen : Component {
    val viewState: MutableValue<ThirdViewState>
    val actions: Actions

    interface Actions {
        fun onBack()
    }
}
