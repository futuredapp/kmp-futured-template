package app.futured.kmptemplate.feature.navigation.signedin.tab.a

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle

internal class TabANavigationComponent(componentContext: ComponentContext) : TabANavigation, ComponentContext by componentContext {

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onCreate() {
                Logger.withTag("Tab A").d { "onCreate" }
            }

            override fun onStart() {
                Logger.withTag("Tab A").d { "onStart" }
            }

            override fun onResume() {
                Logger.withTag("Tab A").d { "onResume" }
            }

            override fun onPause() {
                Logger.withTag("Tab A").d { "onPause" }
            }

            override fun onStop() {
                Logger.withTag("Tab A").d { "onStop" }
            }

            override fun onDestroy() {
                Logger.withTag("Tab A").d { "onDestroy" }
            }
        })
    }
}
