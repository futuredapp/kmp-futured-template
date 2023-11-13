package app.futured.kmptemplate.feature.injection

import app.futured.kmptemplate.feature.navigation.home.HomeStackNavigator
import app.futured.kmptemplate.feature.navigation.root.RootNavigationViewModel
import app.futured.kmptemplate.feature.navigation.root.RootSlotNavigator
import app.futured.kmptemplate.feature.ui.first.FirstViewModel
import app.futured.kmptemplate.feature.ui.login.LoginViewModel
import app.futured.kmptemplate.feature.ui.second.SecondViewModel
import app.futured.kmptemplate.feature.ui.third.ThirdViewModel
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.stack.StackNavigation
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun featureModule() = module {
    factoryOf(::RootNavigationViewModel)
    single { HomeStackNavigator(StackNavigation()) }
    single { RootSlotNavigator(SlotNavigation()) }
    factoryOf(::LoginViewModel)
    factoryOf(::FirstViewModel)
    factoryOf(::SecondViewModel)
    factoryOf(::ThirdViewModel)
}
