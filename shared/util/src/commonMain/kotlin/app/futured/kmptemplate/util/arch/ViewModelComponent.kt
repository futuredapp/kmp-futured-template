package app.futured.kmptemplate.util.arch

import app.futured.kmptemplate.util.ext.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelable
import org.koin.core.component.KoinComponent

/**
 * Base Decompose component that encapsulates a ViewModel and passes ViewModel events back to parent.
 * This is a Koin injection entry point.
 */
abstract class ViewModelComponent<VM : SharedViewModel<*, *>>(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, KoinComponent {

    abstract val viewModel: VM

    private val coroutineScope = componentCoroutineScope()
}

interface StackNavigator<D : Destination<Component>>

interface SlotNavigator<D> where D : Destination<Component>, D : Parcelable

interface Component

interface Destination<out C : Component> {
    fun createComponent(componentContext: ComponentContext): C
}
