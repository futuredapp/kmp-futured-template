package app.futured.kmptemplate.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.futured.kmptemplate.android.ui.navigation.RootNavHostUi
import app.futured.kmptemplate.feature.navigation.root.RootNavHost
import app.futured.kmptemplate.feature.navigation.root.RootNavHostFactory
import app.futured.kmptemplate.feature.ui.base.DefaultAppComponentContext
import app.futured.mdevcamp.android.ui.theme.AppTheme
import com.arkivanov.decompose.retainedComponent

class MainActivity : ComponentActivity() {

    private lateinit var rootNavHost: RootNavHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootNavHost = retainedComponent { retainedContext ->
            RootNavHostFactory.create(DefaultAppComponentContext(retainedContext))
        }
        rootNavHost.handleIntent(intent)

        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    RootNavHostUi(navHost = rootNavHost)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        rootNavHost.handleIntent(intent)
    }

    private fun RootNavHost.handleIntent(intent: Intent?) {
        if (intent==null) {
            return
        }

        val uri = intent.dataString ?: return
        actions.onDeepLink(uri)
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {}
}
