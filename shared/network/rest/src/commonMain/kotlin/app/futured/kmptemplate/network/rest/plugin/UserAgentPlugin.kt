package app.futured.kmptemplate.network.rest.plugin

import app.futured.kmptemplate.network.rest.BuildKonfig
import app.futured.kmptemplate.platform.binding.Platform
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.UserAgent
import org.koin.core.annotation.Single

@Single
internal class UserAgentPlugin(platform: Platform) : HttpClientPlugin {

    private val userAgentString = with(platform) {
        listOf(
            "$appName/$appVersionName",
            "($applicationId; build:$appBuildNumber; $osNameWithVersion; Model:$deviceModel)",
            "ktor-client/${BuildKonfig.ktorUserAgentVersion}",
        )
    }.joinToString(separator = " ")

    override fun install(config: HttpClientConfig<*>) {
        config.install(UserAgent) {
            agent = userAgentString
        }
    }
}
