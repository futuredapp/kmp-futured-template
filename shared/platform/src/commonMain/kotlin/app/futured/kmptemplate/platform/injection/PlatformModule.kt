package app.futured.kmptemplate.platform.injection

import app.futured.kmptemplate.platform.binding.Platform
import app.futured.kmptemplate.platform.binding.PlatformBindings
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

fun platformModule(
    platformBindings: PlatformBindings,
) = module {
    single { platformBindings.platform() }.withOptions { bind<Platform>() }
}
