package app.futured.kmptemplate.app.logging

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity

/**
 * This object takes care of KMP logging setup.
 *
 * We utilize `Kermit`(https://kermit.touchlab.co/) logger for debug logging during development.
 */
internal object AppLogging {

    fun initialize() {
        with(Logger) {
            setMinSeverity(Severity.Debug)
            setTag("KmpTemplate")
            setLogWriters(getPlatformLogWriters())
        }
    }
}

/**
 * Returns list of [LogWriter] instances that shared [Logger] instance should use.
 * This way, we can customize logging behaviour per-platform
 * (eg. Android should not use any log writers in production builds, etc).
 */
internal expect fun getPlatformLogWriters(): List<LogWriter>
