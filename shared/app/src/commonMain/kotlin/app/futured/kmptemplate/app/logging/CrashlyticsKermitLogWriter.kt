package app.futured.kmptemplate.app.logging

import app.futured.kmptemplate.app.crashreporting.CrashlyticsReporter
import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Message
import co.touchlab.kermit.MessageStringFormatter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Tag

/**
 * Custom implementation of Kermit [LogWriter] that logs messages and non-fatal
 * exceptions using provided [crashlytics][CrashlyticsReporter].
 *
 * This implementation,
 * as opposed to [`kermit-crashlytics` implementation](https://kermit.touchlab.co/docs/crashreporting/CRASHLYTICS)),
 * allows you to select which [Throwable]s are logged to Crashlytics to reduce noise.
 *
 * @param minSeverity The minimum log severity that will be handled by this writer. All logs with this and greater severity will
 * be logged as Crashlytics message.
 * @param minCrashSeverity This is the minimum severity that will trigger non-fatal Crashlytics exception log.
 * @param messageStringFormatter [MessageStringFormatter] instance for log formatting (see Kermit docs for more info)
 * @param isLoggable A lambda for filtering [Throwable]s logged to Crashlytics.
 */
internal class CrashlyticsKermitLogWriter(
    private val crashlytics: CrashlyticsReporter,
    private val minSeverity: Severity = Severity.Info,
    private val minCrashSeverity: Severity? = Severity.Warn,
    private val messageStringFormatter: MessageStringFormatter = DefaultFormatter,
    private val isLoggable: (Throwable) -> Boolean,
) : LogWriter() {

    init {
        if (minCrashSeverity != null) {
            require(minSeverity <= minCrashSeverity) {
                "minSeverity ($minSeverity) cannot be greater than minCrashSeverity ($minCrashSeverity)"
            }
        }
    }

    override fun isLoggable(tag: String, severity: Severity): Boolean = severity >= minSeverity

    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        if (throwable != null && !isLoggable(throwable)) {
            return
        }

        val plainMessage = messageStringFormatter.formatMessage(severity, Tag(tag), Message(message))
        crashlytics.logMessage(plainMessage)

        if (minCrashSeverity != null && severity >= minCrashSeverity) {
            if (throwable != null) {
                crashlytics.sendNonFatalException(throwable)
            } else {
                crashlytics.sendNonFatalException(LoggingException(plainMessage))
            }
        }

        if (throwable != null && minCrashSeverity != null && severity >= minCrashSeverity) {
            crashlytics.sendNonFatalException(throwable)
        }
    }
}

/**
 * This exception serves as a way to log Crashlytics messages, using
 * `Logger.e { "Message" }` call, so they display in Crashlytics console as non-fatal exceptions.
 *
 * When above logger call is executed, the [CrashlyticsKermitLogWriter] will wrap provided message in this exception to elevate
 * the plain message log to non-fatal exception in Crashlytics console.
 */
internal class LoggingException(message: String) : IllegalStateException(message)
