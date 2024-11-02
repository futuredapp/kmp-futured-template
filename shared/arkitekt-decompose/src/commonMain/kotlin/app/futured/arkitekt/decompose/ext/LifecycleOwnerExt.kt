package app.futured.arkitekt.decompose.ext

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Creates a Main [CoroutineScope] instance tied to the lifecycle of this [ComponentContext].
 * TODO delete
 */
fun LifecycleOwner.componentCoroutineScope(): CoroutineScope =
    MainScope().also { coroutineScope ->
        lifecycle.doOnDestroy { coroutineScope.cancel() }
    }
