package app.futured.arkitekt.crusecases.base

import app.futured.arkitekt.crusecases.scope.CoroutineScopeOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

abstract class BaseUseCaseExecutionScopeTest : CoroutineScopeOwner {

    private val testDispatcher = StandardTestDispatcher()
    override val viewModelScope = TestScope(testDispatcher)
    override fun getWorkerDispatcher(): CoroutineDispatcher = testDispatcher

    @BeforeTest
    fun setDispatchers() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun cleanupCoroutines() {
        Dispatchers.resetMain()
    }
}
