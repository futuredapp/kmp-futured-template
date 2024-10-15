package app.futured.kmptemplate.feature.tool

import co.touchlab.kermit.Logger
import com.arkivanov.essenty.lifecycle.Lifecycle

class DebugLifecycleCallbacks(tag: String): Lifecycle.Callbacks {

    private val logger = Logger.withTag(tag)

    override fun onCreate() {
        logger.d { "onCreate" }
    }

    override fun onStart() {
        logger.d { "onStart" }
    }

    override fun onResume() {
        logger.d { "onResume" }
    }

    override fun onPause() {
        logger.d { "onPause" }
    }

    override fun onStop() {
        logger.d { "onStop" }
    }

    override fun onDestroy() {
        logger.d { "onDestroy" }
    }
}
