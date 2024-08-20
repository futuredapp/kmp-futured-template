package app.futured.kmptemplate.util.domain

import app.futured.kmptemplate.util.domain.base.BaseUseCaseExecutionScopeTest
import app.futured.kmptemplate.util.domain.error.UseCaseErrorHandler
import app.futured.kmptemplate.util.domain.usecases.TestFailureFlowUseCase
import app.futured.kmptemplate.util.domain.usecases.TestFailureUseCase
import app.futured.kmptemplate.util.domain.usecases.TestFlowUseCase
import app.futured.kmptemplate.util.domain.usecases.TestUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * Sanity check UseCase tests ported from [Arkitekt](https://github.com/futuredapp/arkitekt).
 */
class UseCaseExecutionScopeTest : BaseUseCaseExecutionScopeTest() {

    @BeforeTest
    fun setUp() {
        UseCaseErrorHandler.globalOnErrorLogger = {}
    }

    @AfterTest
    fun tearDown() {
        UseCaseErrorHandler.globalOnErrorLogger = {}
    }

    @Test
    fun `given 1s delay use case when executed two times then first execution cancelled`() {
        val testUseCase = TestUseCase()
        var executionCount = 0

        testUseCase.execute(1) {
            onSuccess { executionCount++ }
            onError {
                fail("Exception thrown where shouldn't")
            }
        }
        viewModelScope.advanceTimeByCompat(500)

        testUseCase.execute(1) {
            onSuccess { executionCount++ }
            onError { fail("Exception thrown where shouldn't") }
        }
        viewModelScope.advanceTimeByCompat(1000)

        assertEquals(1, executionCount)
    }

    @Test
    fun `given failing test use case when executed then indicates onError`() {
        val testFailureUseCase = TestFailureUseCase()
        var resultError: Throwable? = null

        testFailureUseCase.execute(IllegalStateException()) {
            onError { resultError = it }
        }
        viewModelScope.advanceTimeByCompat(1000)

        assertNotNull(resultError)
    }

    @Test
    fun `given test flow use case when executed two times then first execution cancelled`() {
        val testFlowUseCase = TestFlowUseCase()
        val testingList = listOfNotNull(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        val resultList = mutableListOf<Int>()

        testFlowUseCase.execute(TestFlowUseCase.Data(testingList, 1000)) {
            onNext { resultList.add(it) }
            onError { fail("Exception thrown where shouldn't") }
            onComplete { fail("onComplete called where shouldn't") }
        }

        testFlowUseCase.execute(TestFlowUseCase.Data(testingList, 1000)) {
            onNext { resultList.add(it) }
            onError { fail("Exception thrown where shouldn't") }
        }
        viewModelScope.advanceTimeByCompat(10000)

        assertEquals(testingList, resultList)
    }

    @Test
    fun `given test flow use case when executed and all items emitted then completes`() {
        val testFlowUseCase = TestFlowUseCase()
        var completed = false
        val testingList = listOfNotNull(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

        testFlowUseCase.execute(TestFlowUseCase.Data(testingList, 1000)) {
            onError { fail("Exception thrown where shouldn't") }
            onComplete { completed = true }
        }
        viewModelScope.advanceTimeByCompat(10000)

        assertEquals(true, completed)
    }

    @Test
    fun `given failing flow use case when executed then indicates onError`() {
        val testFlowFailureUseCase = TestFailureFlowUseCase()
        var resultError: Throwable? = null

        testFlowFailureUseCase.execute(IllegalStateException()) {
            onNext { fail("onNext called where shouldn't") }
            onError { resultError = it }
            onComplete { fail("onComplete called where shouldn't") }
        }
        viewModelScope.advanceTimeByCompat(1000)

        assertNotNull(resultError)
    }

    @Test
    fun `given success use case launched in coroutine then result is set to success`() {
        val testUseCase = TestUseCase()

        var result: Result<Int>? = null
        viewModelScope.launch {
            result = testUseCase.execute(1)
        }
        viewModelScope.advanceTimeByCompat(10000)

        assertEquals(Result.success(1), result)
    }

    @Test
    fun `given failing use case launched in coroutine then result is set to error`() {
        val testUseCase = TestFailureUseCase()

        var result: Result<Unit>? = null
        viewModelScope.launch {
            result = testUseCase.execute(IllegalStateException())
        }
        viewModelScope.advanceTimeByCompat(10000)

        assertTrue { result?.isFailure == true }
        assertTrue { result?.exceptionOrNull() is IllegalStateException }
    }

    @Test
    fun `given failing use case with CancellationException launched in coroutine then error is rethrown`() {
        val testUseCase = TestFailureUseCase()

        var result: Result<Unit>? = null
        viewModelScope.launch {
            result = testUseCase.execute(CancellationException())
        }
        viewModelScope.advanceTimeByCompat(10000)

        assertNull(result)
    }

    @Test
    fun `given success use case launched two times in coroutine then the first one is cancelled`() {
        val testUseCase = TestUseCase()

        var result: Result<Int>? = null
        viewModelScope.launch {
            testUseCase.execute(1)
            fail("Execute should be cancelled")
        }
        viewModelScope.launch {
            result = testUseCase.execute(1)
        }
        viewModelScope.advanceTimeByCompat(10000)

        assertEquals(Result.success(1), result)
    }

    @Test
    fun `given success use case launched two times with cancelPrevious set to false in coroutine then the first one is not cancelled`() {
        val testUseCase = TestUseCase()

        var result1: Result<Int>? = null
        var result2: Result<Int>? = null
        viewModelScope.launch {
            result1 = testUseCase.execute(1, cancelPrevious = false)
        }
        viewModelScope.launch {
            result2 = testUseCase.execute(2, cancelPrevious = false)
        }
        viewModelScope.advanceTimeByCompat(10000)

        assertEquals(Result.success(1), result1)
        assertEquals(Result.success(2), result2)
    }

    @Test
    fun `when launchWithHandler throws an exception then this exception is send to logUnhandledException and defaultErrorHandler`() {
        var logException: Throwable? = null
        var handlerException: Throwable? = null
        val testOwner = object : BaseUseCaseExecutionScopeTest() {
            override fun defaultErrorHandler(exception: Throwable) {
                handlerException = exception
            }
        }
        UseCaseErrorHandler.globalOnErrorLogger = { exception ->
            logException = exception
        }

        val exception = IllegalStateException()
        testOwner.launchWithHandler { throw exception }
        testOwner.viewModelScope.advanceTimeByCompat(10000)

        assertEquals(exception, logException)
        assertEquals(exception, handlerException)
    }

    @Test
    fun `when launchWithHandler throws an CancellationException then this exception is not send to logUnhandledException and defaultErrorHandler`() {
        var logException: Throwable? = null
        var handlerException: Throwable? = null
        val testOwner = object : BaseUseCaseExecutionScopeTest() {
            override fun defaultErrorHandler(exception: Throwable) {
                handlerException = exception
            }
        }
        UseCaseErrorHandler.globalOnErrorLogger = { exception ->
            logException = exception
        }

        val exception = CancellationException()
        testOwner.launchWithHandler { throw exception }
        testOwner.viewModelScope.advanceTimeByCompat(10000)

        assertEquals(null, logException)
        assertEquals(null, handlerException)
    }

    @Test
    fun `when launchWithHandler throws an CancellationException with non cancellation cause then this exception is send to logUnhandledException only`() {
        var logException: Throwable? = null
        var handlerException: Throwable? = null
        val testOwner = object : BaseUseCaseExecutionScopeTest() {
            override fun defaultErrorHandler(exception: Throwable) {
                handlerException = exception
            }
        }
        UseCaseErrorHandler.globalOnErrorLogger = { exception ->
            logException = exception
        }

        val exception = CancellationException("Message", cause = IllegalStateException())
        testOwner.launchWithHandler { throw exception }
        testOwner.viewModelScope.advanceTimeByCompat(10000)

        assertEquals(exception, logException)
        assertEquals(null, handlerException)
    }

    @Test
    fun `when useCase is executed and onError is called then globalOnErrorLogger is called`() {
        val errorUseCase = TestFailureUseCase()
        var logException: Throwable? = null
        UseCaseErrorHandler.globalOnErrorLogger = { exception ->
            logException = exception
        }

        var resultError: Throwable? = null
        errorUseCase.execute(IllegalStateException()) {
            onError { error ->
                resultError = error
            }
        }
        viewModelScope.advanceTimeByCompat(10000)

        assertTrue(resultError is IllegalStateException)
        assertTrue(logException is IllegalStateException)
    }

    @Test
    fun `when flowUseCase is executed and onError is called then globalOnErrorLogger is called`() {
        val errorUseCase = TestFailureFlowUseCase()
        var logException: Throwable? = null
        UseCaseErrorHandler.globalOnErrorLogger = { exception ->
            logException = exception
        }

        var resultError: Throwable? = null
        errorUseCase.execute(IllegalStateException()) {
            onError { error ->
                resultError = error
            }
        }
        viewModelScope.advanceTimeByCompat(10000)

        assertTrue(resultError is IllegalStateException)
        assertTrue(logException is IllegalStateException)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun TestScope.advanceTimeByCompat(delayTimeMillis: Long) {
        this.testScheduler.apply { advanceTimeBy(delayTimeMillis); runCurrent() }
    }
}
