package app.futured.kmptemplate.feature.injection

import app.futured.kmptemplate.feature.navigation.root.RootNavigationViewModel
import app.futured.kmptemplate.feature.ui.first.FirstViewModel
import app.futured.kmptemplate.feature.ui.login.LoginViewModel
import app.futured.kmptemplate.feature.ui.second.SecondViewModel
import app.futured.kmptemplate.feature.ui.third.ThirdViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureModule() = module {
    factoryOf(::RootNavigationViewModel)
    factoryOf(::LoginViewModel)
    factoryOf(::FirstViewModel)
    factoryOf(::SecondViewModel)
    factoryOf(::ThirdViewModel)
}
