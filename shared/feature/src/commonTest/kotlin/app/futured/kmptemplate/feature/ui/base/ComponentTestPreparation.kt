package app.futured.kmptemplate.feature.ui.base

import app.futured.arkitekt.crusecases.error.UseCaseErrorHandler
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.destroy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

/**
 * Default implementation of [ComponentTest] interface.
 *
 * This class provides a ready-to-use implementation of the [ComponentTest] interface
 * with all the necessary components for testing UI components in a Decompose architecture.
 * It initializes a [kotlinx.coroutines.test.TestScope], [kotlinx.coroutines.test.TestDispatcher], [com.arkivanov.essenty.lifecycle.LifecycleRegistry], and [AppComponentContext]
 * with appropriate default values.
 *
 * The implementation:
 * - Creates a [kotlinx.coroutines.test.TestScope] for coroutine testing
 * - Sets up a [kotlinx.coroutines.test.StandardTestDispatcher] for controlling coroutine execution
 * - Initializes a [com.arkivanov.essenty.lifecycle.LifecycleRegistry] for lifecycle management
 * - Creates a [DefaultAppComponentContext] with a [com.arkivanov.decompose.DefaultComponentContext]
 * - Configures [kotlinx.coroutines.Dispatchers.Main] to use the test dispatcher
 * - Disables UseCase error logging during tests
 *
 * @see ComponentTest
 * @see runComponentTest
 */
class ComponentTestPreparation : ComponentTest {

    /**
     * The test scope used for coroutine testing.
     */
    override val testScope = TestScope()

    /**
     * The test dispatcher used for controlling coroutine execution in tests.
     * Uses the test scheduler from [testScope].
     */
    override val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    /**
     * The lifecycle registry used to manage the component's lifecycle during tests.
     */
    override val lifecycleRegistry = LifecycleRegistry()

    /**
     * The component context used by the component under test.
     * Created with a [com.arkivanov.decompose.DefaultComponentContext] that uses the [lifecycleRegistry].
     */
    override val componentContext = DefaultAppComponentContext(
        DefaultComponentContext(
            lifecycleRegistry
        )
    )

    /**
     * Sets up the test environment before each test.
     *
     * This method:
     * - Sets the main dispatcher to the test dispatcher
     * - Disables UseCase error logging
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun setup() {
        Dispatchers.setMain(testDispatcher)
        UseCaseErrorHandler.globalOnErrorLogger = {}
    }

    /**
     * Cleans up the test environment after each test.
     *
     * This method:
     * - Destroys the lifecycle registry
     * - Resets the main dispatcher
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun cleanup() {
        lifecycleRegistry.destroy()
        Dispatchers.resetMain()
    }
}
