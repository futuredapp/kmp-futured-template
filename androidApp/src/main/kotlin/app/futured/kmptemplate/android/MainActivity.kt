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
import app.futured.kmptemplate.feature_v3.navigation.signedIn.SignedInNavHost
import app.futured.kmptemplate.feature_v3.navigation.signedIn.SignedInNavHostFactory
import app.futured.kmptemplate.feature_v3.ui.base.DefaultAppComponentContext
import com.arkivanov.decompose.retainedComponent

class MainActivity : ComponentActivity() {

    private lateinit var signedInNavHost: SignedInNavHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signedInNavHost = retainedComponent { retainedContext ->
            SignedInNavHostFactory.create(DefaultAppComponentContext(retainedContext))
        }
        signedInNavHost.handleIntent(intent)

        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    RootNavHostUi(navHost = signedInNavHost)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        signedInNavHost.handleIntent(intent)
    }

    private fun SignedInNavHost.handleIntent(intent: Intent?) {
        if (intent == null) {
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
