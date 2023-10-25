package app.futured.kmptemplate.feature.ui.login

import app.futured.kmptemplate.network.graphql.api.RickAndMortyApi
import app.futured.kmptemplate.network.rest.api.StarWarsApi
import app.futured.kmptemplate.util.arch.SharedViewModel
import com.arkivanov.decompose.value.MutableValue

internal class LoginViewModel(
    private val starWarsApi: StarWarsApi,
    private val rickAndMortyApi: RickAndMortyApi,
) : SharedViewModel<LoginViewState, LoginEvent, Nothing>(),
    LoginScreen.Actions {
    override val viewState: MutableValue<LoginViewState> = MutableValue(LoginViewState())

    init {
        testRestApiClient()
        testGraphqlApiClient()
    }

    private fun testRestApiClient() = launchWithHandler {
        val networkResult = starWarsApi.getPerson(3)
        println(networkResult)
    }

    private fun testGraphqlApiClient() = launchWithHandler {
        val networkResult = rickAndMortyApi.getEpisodes()
        println(networkResult)
    }
}
