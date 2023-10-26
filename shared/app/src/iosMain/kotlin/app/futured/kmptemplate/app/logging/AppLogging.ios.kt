package app.futured.kmptemplate.app.logging

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.XcodeSeverityWriter

/**
 * Returns list of [LogWriter] instances that shared [Logger] instance should use.
 * This way, we can customize logging behaviour per-platform
 * (eg. Android should not use any log writers in production builds, etc).
 */
internal actual fun getPlatformLogWriters(): List<LogWriter> = listOf(XcodeSeverityWriter())
