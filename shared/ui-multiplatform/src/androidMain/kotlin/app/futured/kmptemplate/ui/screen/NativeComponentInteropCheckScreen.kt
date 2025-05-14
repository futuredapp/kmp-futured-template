package app.futured.kmptemplate.ui.screen

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.arkitekt.decompose.event.EventsEffect
import app.futured.arkitekt.decompose.event.onEvent
import app.futured.kmptemplate.feature.ui.interopCheck.InteropCheckEvent
import app.futured.kmptemplate.feature.ui.interopCheck.InteropCheckScreen

@Composable
fun NativeComponentInteropCheckScreen(screen: InteropCheckScreen, modifier: Modifier = Modifier){

    val viewState by screen.viewState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    EventsEffect(eventsFlow = screen.events) {
        onEvent<InteropCheckEvent.LaunchWebBrowser> { event ->
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(event.url)))
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                settings.apply {
                    domStorageEnabled = true
                    databaseEnabled = true
                    javaScriptEnabled = true
                }

                clearHistory()
                clearCache(true)
                clearFormData()
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
                CookieManager.getInstance().setAcceptCookie(true)
            }
        },
        update = {
            it.loadUrl(viewState.url)
        }
    )
}
