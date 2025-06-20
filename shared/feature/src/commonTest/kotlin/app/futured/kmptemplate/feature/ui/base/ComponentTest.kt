package app.futured.kmptemplate.feature.ui.base

import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Interface for testing components.
 *
 * This interface provides a standardized way to set up and clean up tests for UI components
 * that use the Decompose framework. It handles the lifecycle management, coroutine dispatchers,
 * and component context required for testing components in isolation.
 *
 * Implementations of this interface should provide the necessary test environment for components,
 * including a [TestScope], [TestDispatcher], [LifecycleRegistry], and [AppComponentContext].
 *
 * @see ComponentTestPreparation for a default implementation
 * @see runComponentTest for a convenient way to run tests with this interface
 */
interface ComponentTest {
    /**
     * The test scope used for coroutine testing.
     */
    val testScope: TestScope

    /**
     * The test dispatcher used for UseCases in components under test.
     */
    val testDispatcher: TestDispatcher

    /**
     * The lifecycle registry used to manage the component's lifecycle during tests.
     */
    val lifecycleRegistry: LifecycleRegistry

    /**
     * The component context used by the component under test.
     */
    val componentContext: AppComponentContext

    /**
     * Sets up the test environment before each test.
     *
     * This method is called before each test and should initialize the test environment.
     */
    @BeforeTest
    fun setup()

    /**
     * Cleans up the test environment after each test.
     *
     * This method is called after each test and should clean up any resources used during the test.
     */
    @AfterTest
    fun cleanup()
}

/**
 * Default timeout for component tests.
 *
 * This value is used as the default timeout for [runComponentTest] if no timeout is specified.
 */
private val DEFAULT_TIMEOUT = 60.seconds

/**
 * Runs a test with the given [ComponentTest] implementation.
 *
 * This extension function provides a convenient way to run tests with a [ComponentTest] implementation.
 * It delegates to [TestScope.runTest] with the provided timeout and test body.
 *
 * Example usage:
 * ```
 * class MyComponentTest : ComponentTestPreparation() {
 *     @Test
 *     fun testMyComponent() = runComponentTest {
 *         // Test code here
 *     }
 * }
 * ```
 *
 * @param timeout The maximum time the test is allowed to run before being cancelled.
 *                Defaults to [DEFAULT_TIMEOUT] (60 seconds).
 * @param testBody The test code to run.
 * @return The result of [TestScope.runTest].
 * @see TestScope.runTest
 */
fun ComponentTest.runComponentTest(
    timeout: Duration = DEFAULT_TIMEOUT,
    testBody: suspend TestScope.() -> Unit,
) = testScope.runTest(timeout, testBody)
