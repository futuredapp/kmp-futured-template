package app.futured.kmptemplate.app.injection

import app.futured.kmptemplate.feature.injection.featureModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * Injection entry-point.
 * This object initialises dependency injection in application.
 */
internal object AppInjection {

    fun initializeInjection(
        appDeclaration: KoinAppDeclaration?,
    ) {
        startKoin {
            if (appDeclaration != null) {
                appDeclaration()
            }

            modules(
                featureModule(),
//                networkModule(),
//                persistenceModule(),
            )
        }
    }
}
