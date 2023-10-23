package app.futured.kmptemplate.platform.injection

import app.futured.kmptemplate.platform.IosPlatform
import app.futured.kmptemplate.platform.Platform
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun kotlinPlatformModule() = module {
    single { IosPlatform } bind Platform::class
}
