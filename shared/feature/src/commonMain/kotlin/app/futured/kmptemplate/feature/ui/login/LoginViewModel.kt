package app.futured.kmptemplate.feature.ui.login

import app.futured.kmptemplate.feature.domain.SaveDummyPersistenceValueUseCase
import app.futured.kmptemplate.feature.navigation.root.RootSlotNavigator
import app.futured.kmptemplate.network.graphql.api.RickAndMortyApi
import app.futured.kmptemplate.network.rest.api.StarWarsApi
import app.futured.kmptemplate.util.arch.SharedViewModel
import co.touchlab.kermit.Logger
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.annotation.Factory

@Factory
internal class LoginViewModel(
    private val starWarsApi: StarWarsApi,
    private val rickAndMortyApi: RickAndMortyApi,
    private val navigator: RootSlotNavigator,
    private val saveDummyPersistenceValueUseCase: SaveDummyPersistenceValueUseCase,
) : SharedViewModel<LoginViewState, Nothing>(),
    LoginScreen.Actions,
    LoginScreen.SuspendActions {

    override val viewState: MutableStateFlow<LoginViewState> = MutableStateFlow(LoginViewState())
    private val logger = Logger.withTag("LoginViewModel")

    init {
        testRestApiClient()
        testGraphqlApiClient()

        // Just to verify [ApplicationSettingsPersistence] gets properly injected
        launchWithHandler {
            saveDummyPersistenceValueUseCase.execute(false).getOrThrow()
        }
    }

    private fun testRestApiClient() = launchWithHandler {
        val networkResult = starWarsApi.getPerson(3)
        logger.d { networkResult.toString() }
    }

    private fun testGraphqlApiClient() = launchWithHandler {
        val networkResult = rickAndMortyApi.getEpisodes()
        logger.d { networkResult.toString() }
    }

    override fun onLoginClick() = navigator.showHome()

    override suspend fun refresh() {
        // Lets SwiftUI display refresh indicator for as long as data is being refreshed
        coroutineScope {
            val starWarsCall = async { starWarsApi.getPerson(3) }
            val rickAndMortyCall = async { rickAndMortyApi.getEpisodes() }
            starWarsCall.await().let { logger.d { it.toString() } }
            rickAndMortyCall.await().let { logger.d { it.toString() } }
        }
    }
}
