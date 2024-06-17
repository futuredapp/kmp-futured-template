package app.futured.kmptemplate.util.ext

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * Creates a Main [CoroutineScope] instance tied to the lifecycle of this [ComponentContext].
 */
fun ComponentContext.componentCoroutineScope(): CoroutineScope =
    MainScope().also { coroutineScope ->
        lifecycle.doOnDestroy { coroutineScope.cancel() }
    }
