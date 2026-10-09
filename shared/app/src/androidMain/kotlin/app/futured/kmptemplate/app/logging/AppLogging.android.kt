package app.futured.kmptemplate.app.logging

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.LogcatWriter

/**
 * Returns list of [LogWriter] instances that shared [Logger] instance should use.
 * This way, we can customize logging behaviour per-platform
 * (eg. Android should not use any log writers in production builds, etc).
 */
internal actual fun getPlatformLogWriters(isDebug: Boolean): List<LogWriter> = if (isDebug) {
    listOf(LogcatWriter())
} else {
    emptyList()
}
