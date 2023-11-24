package app.futured.kmptemplate.feature.injection

import app.futured.kmptemplate.feature.navigation.home.HomeStackNavigator
import app.futured.kmptemplate.feature.navigation.root.RootSlotNavigator
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.stack.StackNavigation
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("app.futured.kmptemplate.feature")
class FeatureModule {
    @Single
    fun homeStackNavigator() = HomeStackNavigator(StackNavigation())

    @Single
    fun rootSlotNavigator() = RootSlotNavigator(SlotNavigation())

}
