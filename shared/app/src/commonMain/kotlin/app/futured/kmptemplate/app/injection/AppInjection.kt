package app.futured.kmptemplate.app.injection

import app.futured.kmptemplate.feature.injection.FeatureModule
import app.futured.kmptemplate.network.graphql.injection.NetworkGraphqlModule
import app.futured.kmptemplate.network.rest.injection.NetworkRestModule
import app.futured.kmptemplate.persistence.injection.persistenceModule
import app.futured.kmptemplate.platform.binding.PlatformBindings
import app.futured.kmptemplate.platform.injection.platformModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module

/**
 * Injection entry-point.
 * This object initialises dependency injection in application.
 */
internal object AppInjection {

    fun initializeInjection(
        platformBindings: PlatformBindings,
        appDeclaration: KoinAppDeclaration?,
    ) {
        startKoin {
            printLogger(Level.ERROR)

            if (appDeclaration != null) {
                appDeclaration()
            }

            modules(
                platformModule(platformBindings = platformBindings),
                FeatureModule().module,
                NetworkGraphqlModule().module,
                NetworkRestModule().module,
                persistenceModule(),
            )
        }
    }
}
