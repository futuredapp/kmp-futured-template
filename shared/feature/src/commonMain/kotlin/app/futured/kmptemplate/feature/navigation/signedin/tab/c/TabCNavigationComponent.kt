package app.futured.kmptemplate.feature.navigation.signedin.tab.c

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle

internal class TabCNavigationComponent(componentContext: ComponentContext) : TabCNavigation, ComponentContext by componentContext {

    init {
        lifecycle.subscribe(object : Lifecycle.Callbacks {
            override fun onCreate() {
                Logger.withTag("Tab C").d { "onCreate" }
            }

            override fun onStart() {
                Logger.withTag("Tab C").d { "onStart" }
            }

            override fun onResume() {
                Logger.withTag("Tab C").d { "onResume" }
            }

            override fun onPause() {
                Logger.withTag("Tab C").d { "onPause" }
            }

            override fun onStop() {
                Logger.withTag("Tab C").d { "onStop" }
            }

            override fun onDestroy() {
                Logger.withTag("Tab C").d { "onDestroy" }
            }
        })
    }
}
