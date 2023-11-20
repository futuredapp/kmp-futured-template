package app.futured.kmptemplate.app.injection

import app.futured.kmptemplate.feature.injection.FeatureModule
import app.futured.kmptemplate.network.graphql.injection.NetworkGraphqlModule
import app.futured.kmptemplate.network.rest.injection.NetworkRestModule
import app.futured.kmptemplate.platform.binding.PlatformBindings
import app.futured.kmptemplate.feature.injection.featureModule
import app.futured.kmptemplate.network.graphql.injection.networkGraphqlModule
import app.futured.kmptemplate.network.rest.injection.networkRestModule
import app.futured.kmptemplate.persistence.injection.persistenceModule
import app.futured.kmptemplate.platform.injection.NativePlatformModule
import app.futured.kmptemplate.platform.injection.platformModule
import org.koin.core.context.startKoin
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
            if (appDeclaration != null) {
                appDeclaration()
            }

            modules(
                platformModule(platformBindings = platformBindings),
                FeatureModule().module,
                NetworkGraphqlModule().module,
                NetworkRestModule().module,
            )
        }
    }
}
