package app.futured.kmptemplate.app.injection

import app.futured.kmptemplate.feature.injection.featureModule
import app.futured.kmptemplate.network.graphql.injection.networkGraphqlModule
import app.futured.kmptemplate.network.rest.injection.networkRestModule
import app.futured.kmptemplate.platform.injection.NativePlatformModule
import app.futured.kmptemplate.platform.injection.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

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
                featureModule(),
                networkGraphqlModule(),
                networkRestModule()
            )
        }
    }
}
