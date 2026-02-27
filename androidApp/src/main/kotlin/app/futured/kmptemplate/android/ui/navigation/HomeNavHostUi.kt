package app.futured.kmptemplate.android.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.ui.screen.FirstScreenUi
import app.futured.kmptemplate.ui.screen.PickerScreenUi
import app.futured.kmptemplate.ui.screen.SecondScreenUi
import app.futured.kmptemplate.ui.screen.ThirdScreenUi
import app.futured.kmptemplate.feature.navigation.home.HomeChild
import app.futured.kmptemplate.feature.navigation.home.HomeConfig
import app.futured.kmptemplate.feature.navigation.home.HomeNavHost
import app.futured.kmptemplate.feature.navigation.home.HomeSheetChild
import app.futured.kmptemplate.feature.navigation.home.HomeSheetConfig
import app.futured.kmptemplate.feature.ui.picker.PickerScreen
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.stack.ChildStack

@OptIn(ExperimentalDecomposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeNavHostUi(
    navHost: HomeNavHost,
    modifier: Modifier = Modifier,
) {
    val stack: ChildStack<HomeConfig, HomeChild> by navHost.stack.collectAsStateWithLifecycle()
    val sheet: ChildSlot<HomeSheetConfig, HomeSheetChild> by navHost.sheet.collectAsStateWithLifecycle()
    val actions = navHost.actions
    val bottomSheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.navigationBars,
        content = { paddings ->
            Children(
                stack = stack,
                modifier = Modifier.padding(paddings),
                animation = predictiveBackAnimation(
                    backHandler = navHost.backHandler,
                    onBack = actions::pop,
                ),
            ) { child ->
                when (val childInstance = child.instance) {
                    is HomeChild.First -> FirstScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
                    is HomeChild.Second -> SecondScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
                    is HomeChild.Third -> ThirdScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
                }
            }
        },
    )

    val sheetChildInstance = sheet.child?.instance
    when (sheetChildInstance) {
        is HomeSheetChild.Picker -> PickerSheet(
            screen = sheetChildInstance.screen,
            bottomSheetState = bottomSheetState,
            onDismissRequest = actions::onSheetDismissed,
        )

        null -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PickerSheet(
    screen: PickerScreen,
    bottomSheetState: SheetState,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismissRequest,
        contentWindowInsets = { WindowInsets(0) },
        modifier = modifier,
    ) {
        PickerScreenUi(screen)
    }
}
