package app.futured.kmptemplate.feature.domain

import app.futured.arkitekt.crusecases.FlowUseCase
import app.futured.arkitekt.crusecases.error.UseCaseErrorHandler
import app.futured.arkitekt.crusecases.scope.CoroutineScopeOwner
import app.futured.arkitekt.crusecases.scope.FlowUseCaseConfig
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.subscribe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.koin.core.annotation.Factory
import kotlin.coroutines.cancellation.CancellationException

@Factory
internal class TimeStampUseCase() : FlowUseCase<Unit, Instant>() {
    override fun build(args: Unit): Flow<Instant> = flow {
        for (i in 0..1000) {
            emit(Clock.System.now())
            delay(1000)
        }
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
context(coroutineScopeOwner: CoroutineScopeOwner, componentContext: LifecycleOwner)
fun <ARGS, T : Any?> FlowUseCase<ARGS, T>.executeWithLifecycle(
    args: ARGS,
    config: FlowUseCaseConfig.Builder<T, T>.() -> Unit,
) {
    val flowUseCaseConfig = FlowUseCaseConfig.Builder<T, T>().run {
        config.invoke(this)
        return@run build()
    }

    if (flowUseCaseConfig.disposePrevious) {
        job?.cancel()
    }

    val lifecycleState = MutableStateFlow(false)
    componentContext.lifecycle.subscribe(
        onResume = {
            lifecycleState.value = true
        },
        onPause = {
            lifecycleState.value = false
        }
    )
    val targetFlow = build(args)

    job = lifecycleState
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
