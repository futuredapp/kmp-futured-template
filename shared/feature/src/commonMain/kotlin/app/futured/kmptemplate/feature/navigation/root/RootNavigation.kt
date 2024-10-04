package app.futured.kmptemplate.feature.navigation.root

import com.arkivanov.decompose.router.slot.ChildSlot
import kotlinx.coroutines.flow.StateFlow

interface RootNavigation {
    val slot: StateFlow<ChildSlot<RootDestination, RootEntry>>
    val actions: Actions

    interface Actions {
        fun openDeepLink(uri: String)
    }
}
