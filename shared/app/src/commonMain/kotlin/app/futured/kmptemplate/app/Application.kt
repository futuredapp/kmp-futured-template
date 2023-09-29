package app.futured.kmptemplate.app

import app.futured.kmptemplate.app.injection.AppInjection
import org.koin.dsl.KoinAppDeclaration

/**
 * Shared KMP entrypoint.
 *
 * Initializes injection framework,
 */
object Application {

    fun initializeSharedApplication(
        appDeclaration: KoinAppDeclaration? = null,
    ) {
        AppInjection.initializeInjection(
            appDeclaration,
        )
    }
}
