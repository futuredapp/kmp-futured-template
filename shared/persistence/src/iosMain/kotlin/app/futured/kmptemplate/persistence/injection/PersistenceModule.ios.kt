package app.futured.kmptemplate.persistence.injection

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("app.futured.kmptemplate.persistence.platform")
actual class PersistencePlatformModule actual constructor()
