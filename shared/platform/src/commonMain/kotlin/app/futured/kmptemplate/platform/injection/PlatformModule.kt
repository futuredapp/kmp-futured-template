package app.futured.kmptemplate.platform.injection

import org.koin.dsl.module

fun platformModule(
    nativePlatformModule: NativePlatformModule,
) = module {
    includes(kotlinPlatformModule())
}
