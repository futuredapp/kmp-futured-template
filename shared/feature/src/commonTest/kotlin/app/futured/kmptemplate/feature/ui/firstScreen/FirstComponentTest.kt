package app.futured.kmptemplate.feature.ui.firstScreen

import app.cash.turbine.Turbine
import app.cash.turbine.turbineScope
import app.futured.kmptemplate.feature.domain.CounterUseCase
import app.futured.kmptemplate.feature.domain.FetchDataUseCase
import app.futured.kmptemplate.feature.navigation.home.HomeConfig
import app.futured.kmptemplate.feature.ui.base.ComponentTest
import app.futured.kmptemplate.feature.ui.base.ComponentTestPreparation
import app.futured.kmptemplate.feature.ui.base.runComponentTest
import app.futured.kmptemplate.network.rest.dto.Person
import app.futured.kmptemplate.network.rest.result.NetworkError
import com.arkivanov.essenty.lifecycle.create
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.io.IOException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class FirstComponentTest : ComponentTest by ComponentTestPreparation() {

    private class FakeNavigation : FirstScreenNavigation {
        val calls = Turbine<HomeConfig>()

        override fun FirstComponent.navigateToSecond() {
            calls.add(HomeConfig.Second)
        }
    }

    private fun createComponent(
        fetchDataUseCase: FetchDataUseCase,
        counterUseCase: CounterUseCase,
        navigation: FirstScreenNavigation = FakeNavigation(),
    ): FirstComponent = FirstComponent(
        lifecycleScope = testScope,
        useCaseDispatcher = testDispatcher,
        componentContext = componentContext,
        fetchDataUseCase = fetchDataUseCase,
        counterUseCase = counterUseCase,
        navigation = navigation,
    )

    @Test
    fun `when component is created, initial state has correct defaults`() = runComponentTest {
        val component = createComponent(
            fetchDataUseCase = { error("noop") },
            counterUseCase = { flow { /* empty flow */ } },
        )

        // Component starts with default state
        val initialState = component.viewState.value
        assertEquals(0, initialState.counter)
        assertEquals(null, initialState.randomPerson)
        assertEquals(null, initialState.randomPersonError)
    }

    @Test
    fun `when lifecycle is created, person is fetched and state updated`() = runComponentTest {
        val expectedPerson = Person(name = "Luke Skywalker")
        val component = createComponent(
            fetchDataUseCase = {
                delay(3.seconds)
                expectedPerson
            },
            counterUseCase = { flowOf() },
        )

        turbineScope {
            val state = component.viewState.testIn(backgroundScope)
            lifecycleRegistry.create()

            state.skipItems(1) // Skip default state
            assertEquals(expectedPerson, state.awaitItem().randomPerson)
        }
    }

    @Test
    fun `when fetchDataUseCase fails, viewState contains error`() = runComponentTest {
        val expectedError = NetworkError.ConnectionError(IOException())
        val component = createComponent(
            fetchDataUseCase = { throw expectedError },
            counterUseCase = { flowOf() },
        )

        turbineScope {
            val state = component.viewState.testIn(backgroundScope)
            lifecycleRegistry.create()

            state.skipItems(1) // Skip default state
            assertEquals(expectedError, state.awaitItem().randomPersonError)
        }
    }

    @Test
    fun `counter updates viewState correctly with each emission`() = runComponentTest {
        val component = createComponent(
            fetchDataUseCase = { error("noop") },
            counterUseCase = {
                flow {
                    repeat(6) { rep ->
                        emit(rep.toLong())
                        delay(it.interval)
                    }
                }
            },
        )

        turbineScope {
            val states = component.viewState.testIn(backgroundScope)
            lifecycleRegistry.create()
            states.skipItems(1) // Skip initial state
            assertEquals(0L, states.awaitItem().counter)
            assertEquals(1L, states.awaitItem().counter)
            assertEquals(2L, states.awaitItem().counter)
            assertEquals(3L, states.awaitItem().counter)
            assertEquals(4L, states.awaitItem().counter)
            assertEquals(5L, states.awaitItem().counter)
        }
    }

    @Test
    fun `when counter reaches 30, Notify event is emitted`() = runComponentTest {
        val component = createComponent(
            fetchDataUseCase = { error("noop") },
            counterUseCase = {
                flow {
                    repeat(40) { rep ->
                        emit(rep.toLong())
                        delay(it.interval)
                    }
                }
            },
        )

        turbineScope {
            val events = component.events.testIn(backgroundScope)
            lifecycleRegistry.create()

            events.awaitItem().run {
                assertIs<FirstUiEvent.Notify>(this)
                assertEquals(30, this.count)
            }
        }
    }

    @Test
    fun `when counter reaches 10, no Notify event is emitted`() = runComponentTest {
        val component = createComponent(
            fetchDataUseCase = { error("noop") },
            counterUseCase = {
                flow {
                    repeat(10) { rep ->
                        emit(rep.toLong())
                        delay(it.interval)
                    }
                }
            },
        )

        turbineScope {
            val events = component.events.testIn(backgroundScope)
            lifecycleRegistry.create()
            events.expectNoEvents()
        }
    }

    @Test
    fun `state updates and events are both in correct order`() = runComponentTest {
        val expectedPerson = Person("Darth Vader")
        val component = createComponent(
            fetchDataUseCase = { expectedPerson },
            counterUseCase = { flowOf(29, 30, 31, 40) },
        )

        turbineScope {
            val state = component.viewState.testIn(backgroundScope)
            val events = component.events.testIn(backgroundScope)

            lifecycleRegistry.create()

            state.awaitItem().run {
                assertEquals(0, counter)
                assertEquals(null, randomPerson)
            }
            state.awaitItem().run {
                assertEquals(0, counter)
                assertEquals(expectedPerson, randomPerson)
            }
            state.awaitItem().run {
                assertEquals(29, counter)
                assertEquals(expectedPerson, randomPerson)
            }
            state.awaitItem().run {
                assertEquals(30, counter)
                assertEquals(expectedPerson, randomPerson)
            }

            // Event should be sent after counter reaches 30
            assertEquals(FirstUiEvent.Notify(30), events.awaitItem())

            state.awaitItem().run {
                assertEquals(31, counter)
                assertEquals(expectedPerson, randomPerson)
            }
            state.awaitItem().run {
                assertEquals(40, counter)
                assertEquals(expectedPerson, randomPerson)
            }
        }
    }

    @Test
    fun `when user taps next button, navigator is called`() = runComponentTest {
        val navigation = FakeNavigation()
        val component = createComponent(
            fetchDataUseCase = { error("noop") },
            counterUseCase = { flowOf() },
            navigation = navigation,
        )

        component.actions.onNext()
        assertEquals(HomeConfig.Second, navigation.calls.awaitItem())
    }
}
