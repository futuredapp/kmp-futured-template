package app.futured.kmptemplate.feature.ui.login

import app.futured.kmptemplate.network.graphql.api.RickAndMortyApi
import app.futured.kmptemplate.network.rest.api.StarWarsApi
import app.futured.kmptemplate.util.arch.SharedViewModel
import co.touchlab.kermit.Logger
import com.arkivanov.decompose.value.MutableValue

internal class LoginViewModel(
    private val starWarsApi: StarWarsApi,
    private val rickAndMortyApi: RickAndMortyApi,
) : SharedViewModel<LoginViewState, LoginEvent, Nothing>(),
    LoginScreen.Actions {

    override val viewState: MutableValue<LoginViewState> = MutableValue(LoginViewState())
    private val logger = Logger.withTag("LoginViewModel")

    init {
        testRestApiClient()
        testGraphqlApiClient()
    }

    private fun testRestApiClient() = launchWithHandler {
        val networkResult = starWarsApi.getPerson(3)
        logger.d { networkResult.toString() }
    }

    private fun testGraphqlApiClient() = launchWithHandler {
        val networkResult = rickAndMortyApi.getEpisodes()
        logger.d { networkResult.toString() }
    }

    override fun onLoginClick() = sendOutput(LoginEvent.NavigateToHome)
}
