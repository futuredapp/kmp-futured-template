package app.futured.kmptemplate.app

import app.futured.kmptemplate.app.crashreporting.AppCrashReporting
import app.futured.kmptemplate.app.crashreporting.CrashlyticsReporter
import app.futured.kmptemplate.app.injection.AppInjection
import app.futured.kmptemplate.app.logging.AppLogging
import app.futured.kmptemplate.platform.binding.PlatformBindings
import org.koin.dsl.KoinAppDeclaration

/**
 * Shared KMP entrypoint.
 *
 * Initializes injection framework,
 */
object KmpApplication {

    fun initializeSharedApplication(
        platformBindings: PlatformBindings,
        appDeclaration: KoinAppDeclaration? = null,
    ) {
        val crashlyticsReporter = CrashlyticsReporter(platformBindings.firebaseCrashlytics())

        AppInjection.initializeInjection(
            platformBindings = platformBindings,
            appDeclaration = appDeclaration,
        )
        AppCrashReporting.initialize(crashlyticsReporter)
        AppLogging.initialize(crashlyticsReporter)
    }
}
