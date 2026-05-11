package app.futured.kmptemplate.app.injection

import app.futured.kmptemplate.platform.binding.PlatformBindings
import app.futured.kmptemplate.platform.injection.platformModule
import org.koin.core.annotation.KoinApplication
import org.koin.dsl.KoinAppDeclaration
import org.koin.plugin.module.dsl.startKoin

/**
 * Injection entry-point.
 * This object initializes dependency injection in application.
 */
@KoinApplication
internal object AppInjection {

    fun initializeInjection(
        platformBindings: PlatformBindings,
        appDeclaration: KoinAppDeclaration?,
    ) {
        startKoin<AppInjection> {
            if (appDeclaration != null) {
                appDeclaration()
            }

            modules(
                platformModule(platformBindings),
            )
        }
    }
}
