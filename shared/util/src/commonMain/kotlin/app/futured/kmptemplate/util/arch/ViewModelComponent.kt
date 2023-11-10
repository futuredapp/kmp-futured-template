package app.futured.kmptemplate.util.arch

import app.futured.kmptemplate.util.ext.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Base Decompose component that encapsulates a ViewModel and passes ViewModel events back to parent.
 * This is a Koin injection entry point.
 */
abstract class ViewModelComponent<VM : SharedViewModel<*, OUT_EVENT, *>, OUT_EVENT : OutputEvent<*>>(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, KoinComponent {

    abstract val viewModel: VM
    abstract val output: (OUT_EVENT) -> Unit

    private val coroutineScope = componentCoroutineScope()

    init {
        coroutineScope.launch {
            viewModel.outputEvents.collect {
                output(it)
            }
        }
    }
}

interface Navigator<D : Destination<Component>> {
    fun push(destination: D)
    fun pop()
}

interface Component

interface Destination<out Comp : Component> {
    fun createComponent(componentContext: ComponentContext): Comp
}
