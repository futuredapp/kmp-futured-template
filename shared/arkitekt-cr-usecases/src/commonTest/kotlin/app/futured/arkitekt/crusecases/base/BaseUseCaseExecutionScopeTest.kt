package app.futured.arkitekt.crusecases.base

import app.futured.arkitekt.crusecases.scope.UseCaseExecutionScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseUseCaseExecutionScopeTest : UseCaseExecutionScope {

    override val useCaseJobPool: MutableMap<Any, Job> = mutableMapOf()

    private val testDispatcher = StandardTestDispatcher()
    override val useCaseScope = TestScope(testDispatcher)
    override fun getWorkerDispatcher(): CoroutineDispatcher = testDispatcher

    @BeforeTest
    fun setDispatchers() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun cleanupCoroutines() {
        useCaseScope.cancel()
        Dispatchers.resetMain()
    }
}
