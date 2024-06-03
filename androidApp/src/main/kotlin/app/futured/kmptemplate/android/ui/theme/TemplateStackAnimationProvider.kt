package app.futured.kmptemplate.android.ui.theme

import app.futured.kmptemplate.android.tools.Constants
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimationProvider
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation

/**
 * Stack animation provides animation for all stacks, not necessary to implement fallback animation in children components.
 */
object TemplateStackAnimationProvider : StackAnimationProvider {
    override fun <C : Any, T : Any> provide(): StackAnimation<C, T> {
        return stackAnimation(Constants.Navigation.STACK_ANIMATION_CROSS_FADE_SPEC)
    }
}
