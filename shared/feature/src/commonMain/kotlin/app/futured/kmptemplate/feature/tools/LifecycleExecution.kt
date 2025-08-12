package app.futured.kmptemplate.feature.tools

import app.futured.arkitekt.crusecases.FlowUseCase
import app.futured.arkitekt.crusecases.error.UseCaseErrorHandler
import app.futured.arkitekt.crusecases.scope.CoroutineScopeOwner
import app.futured.arkitekt.crusecases.scope.FlowUseCaseConfig
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.subscribe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalCoroutinesApi::class)
context(coroutineScopeOwner: CoroutineScopeOwner, componentContext: LifecycleOwner)
fun <ARGS, T : Any?> FlowUseCase<ARGS, T>.executeWithLifecycle(
    args: ARGS,
    minActiveState: Lifecycle.State = Lifecycle.State.RESUMED,
    config: FlowUseCaseConfig.Builder<T, T>.() -> Unit,
) = executeWithLifecycleInternal(args = args, minActiveState = minActiveState, config = config)

@OptIn(ExperimentalCoroutinesApi::class)
context(coroutineScopeOwner: CoroutineScopeOwner, componentContext: LifecycleOwner)
fun <T : Any?> FlowUseCase<Unit, T>.executeWithLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.RESUMED,
    config: FlowUseCaseConfig.Builder<T, T>.() -> Unit,
) = executeWithLifecycleInternal(args = Unit, minActiveState = minActiveState, config = config)

@OptIn(ExperimentalCoroutinesApi::class)
context(coroutineScopeOwner: CoroutineScopeOwner, componentContext: LifecycleOwner)
private fun <ARGS, T : Any?> FlowUseCase<ARGS, T>.executeWithLifecycleInternal(
    args: ARGS,
    minActiveState: Lifecycle.State = Lifecycle.State.RESUMED,
    config: FlowUseCaseConfig.Builder<T, T>.() -> Unit,
) {
    val flowUseCaseConfig = FlowUseCaseConfig.Builder<T, T>().run {
        config.invoke(this)
        return@run build()
    }

    if (flowUseCaseConfig.disposePrevious) {
        job?.cancel()
    }

    val enableExecution = MutableStateFlow(false)

    componentContext.lifecycle.subscribe(
        onCreate = {
            if (minActiveState == Lifecycle.State.CREATED) {
                enableExecution.value = true
            }
        },
        onDestroy = {
            if (minActiveState == Lifecycle.State.CREATED) {
                enableExecution.value = false
            }
        },
        onStart = {
            if (minActiveState == Lifecycle.State.STARTED) {
                enableExecution.value = true
            }
        },
        onStop = {
            if (minActiveState == Lifecycle.State.STARTED) {
                enableExecution.value = false
            }
        },
        onResume = {
            if (minActiveState == Lifecycle.State.RESUMED) {
                enableExecution.value = true
            }
        },
        onPause = {
            if (minActiveState == Lifecycle.State.RESUMED) {
                enableExecution.value = false
            }
        },
    )
    val targetFlow = build(args)

    job = enableExecution
        .flatMapLatest {
            if (it) targetFlow else emptyFlow()
        }
        .flowOn(coroutineScopeOwner.getWorkerDispatcher())
        .onStart { flowUseCaseConfig.onStart() }
        .onEach { flowUseCaseConfig.onNext(it) }
        .onCompletion { error ->
            when {
                error is CancellationException -> {
                    // ignore this exception
                }

                error != null -> {
                    UseCaseErrorHandler.globalOnErrorLogger(error)
                    flowUseCaseConfig.onError(error)
                }

                else -> flowUseCaseConfig.onComplete()
            }
        }
        .catch { /* handled in onCompletion */ }
        .launchIn(coroutineScopeOwner.viewModelScope)
}
