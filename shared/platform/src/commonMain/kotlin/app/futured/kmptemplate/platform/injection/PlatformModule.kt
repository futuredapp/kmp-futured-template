package app.futured.kmptemplate.platform.injection

import app.futured.kmptemplate.platform.binding.PlatformBindings
import org.koin.dsl.module

fun platformModule(
    platformBindings: PlatformBindings,
) = module {
    includes(kotlinPlatformModule())
}
