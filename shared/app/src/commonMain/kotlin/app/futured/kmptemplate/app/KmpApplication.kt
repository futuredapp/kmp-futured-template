package app.futured.kmptemplate.app

import app.futured.kmptemplate.app.injection.AppInjection
import app.futured.kmptemplate.platform.injection.NativePlatformModule
import org.koin.dsl.KoinAppDeclaration

/**
 * Shared KMP entrypoint.
 *
 * Initializes injection framework,
 */
object KmpApplication {

    fun initializeSharedApplication(
        nativePlatformModule: NativePlatformModule,
        appDeclaration: KoinAppDeclaration? = null,
    ) {
        AppInjection.initializeInjection(
            nativePlatformModule = nativePlatformModule,
            appDeclaration = appDeclaration,
        )

        println("Shared application was initialized.")
    }
}
