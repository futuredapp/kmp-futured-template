package app.futured.kmptemplate.android.ui.navigation

// TODO v3
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.semantics.semantics
//import androidx.compose.ui.semantics.testTagsAsResourceId
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import app.futured.kmptemplate.android.ui.screen.LoginScreenUi
//import app.futured.kmptemplate.feature.navigation.root.RootDestination
//import app.futured.kmptemplate.feature.navigation.root.RootEntry
//import app.futured.kmptemplate.feature.navigation.root.RootNavigation
//import com.arkivanov.decompose.router.slot.ChildSlot
//
//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun RootNavGraph(
//    rootNavigation: RootNavigation,
//    modifier: Modifier = Modifier,
//) {
//    val slot: ChildSlot<RootDestination, RootEntry> by rootNavigation.slot.collectAsStateWithLifecycle()
//
//    Surface(
//        modifier.semantics {
//            testTagsAsResourceId = true
//        },
//        color = MaterialTheme.colorScheme.background,
//    ) {
//        when (val childInstance = slot.child?.instance) {
//            is RootEntry.Login -> LoginScreenUi(
//                screen = childInstance.instance,
//                modifier = Modifier.fillMaxSize(),
//            )
//
//            is RootEntry.SignedIn -> SignedInNavGraph(
//                navigation = childInstance.instance,
//                modifier = Modifier.fillMaxSize(),
//            )
//
//            null -> Unit
//        }
//    }
//}
