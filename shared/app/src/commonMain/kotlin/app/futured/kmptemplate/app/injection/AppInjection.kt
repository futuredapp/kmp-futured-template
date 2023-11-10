package app.futured.kmptemplate.app.injection

import app.futured.kmptemplate.feature.injection.FeatureModule
import app.futured.kmptemplate.network.graphql.injection.NetworkGraphqlModule
import app.futured.kmptemplate.network.rest.injection.NetworkRestModule
import app.futured.kmptemplate.platform.injection.NativePlatformModule
import app.futured.kmptemplate.platform.injection.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.*

/**
 * Injection entry-point.
 * This object initialises dependency injection in application.
 */
internal object AppInjection {

    fun initializeInjection(
        nativePlatformModule: NativePlatformModule,
        appDeclaration: KoinAppDeclaration?,
    ) {
        startKoin {
            if (appDeclaration != null) {
                appDeclaration()
            }

            modules(
                platformModule(nativePlatformModule = nativePlatformModule),
                FeatureModule().module,
                NetworkGraphqlModule().module,
                NetworkRestModule().module,
            )
        }
    }
}
