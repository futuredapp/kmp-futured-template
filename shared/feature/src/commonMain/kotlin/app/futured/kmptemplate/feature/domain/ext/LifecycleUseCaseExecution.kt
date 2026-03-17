package app.futured.kmptemplate.feature.domain.ext

import app.futured.arkitekt.crusecases.FlowUseCase
import app.futured.arkitekt.crusecases.error.UseCaseErrorHandler
import app.futured.arkitekt.crusecases.scope.CoroutineScopeOwner
import app.futured.arkitekt.crusecases.scope.FlowUseCaseConfig
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.subscribe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.cancellation.CancellationException

/**
 * Lifecycle-aware variant of [FlowUseCaseExecution] execution. Unlike the standard `execute`, this function
 * automatically **pauses** the upstream flow when the component enters the background (onPause) and
 * **resumes** it when the component comes back to the foreground (onResume), without cancelling
 * the coroutine or losing the subscription.
 *
 * This is the key advantage over `execute`: with `execute` the flow keeps collecting even when the
 * UI is invisible, which wastes resources and may trigger UI updates on a hidden screen. With
 * `executeWithLifecycle` the active collection is gated by the lifecycle state — the flow emits
 * only while the component is resumed.
 *
 * Requires both [CoroutineScopeOwner] (to manage the coroutine job pool) and [LifecycleOwner]
 * (to observe resume/pause transitions) as context receivers.
 *
 * @param args Arguments passed to the use case.
 * @param config [FlowUseCaseConfig] builder used to handle `onStart`, `onNext`, `onError`, and
 * `onComplete` callbacks, as well as `disposePrevious` configuration.
 */
@OptIn(ExperimentalCoroutinesApi::class)
context(coroutineScopeOwner: CoroutineScopeOwner, lifecycleOwner: LifecycleOwner)
fun <ARGS, T : Any?> FlowUseCase<ARGS, T>.executeWithLifecycle(
    args: ARGS,
    config: FlowUseCaseConfig.Builder<T, T>.() -> Unit,
) {
    val flowUseCaseConfig = FlowUseCaseConfig.Builder<T, T>().run {
        config.invoke(this)
        return@run build()
    }

    if (flowUseCaseConfig.disposePrevious) {
        coroutineScopeOwner.useCaseJobPool[this]?.cancel()
    }

    val lifecycleState = MutableStateFlow(false)
    lifecycleOwner.lifecycle.subscribe(
        onResume = { lifecycleState.value = true },
        onPause = { lifecycleState.value = false },
    )
    val targetFlow = build(args)

    coroutineScopeOwner.useCaseJobPool[this] = lifecycleState
        .flatMapLatest { active ->
            if (active) targetFlow else emptyFlow()
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
        .launchIn(coroutineScopeOwner.useCaseScope)
}
