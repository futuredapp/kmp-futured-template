@file:Suppress("MagicNumber")

package app.futured.kmptemplate.ui.tools

import androidx.compose.animation.core.tween
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale

interface Constants {

    object Navigation {
        private val STACK_ANIMATION_TWEEN = tween<Float>(100)
        val STACK_ANIMATION_CROSS_FADE_SPEC = fade(STACK_ANIMATION_TWEEN) + scale(STACK_ANIMATION_TWEEN)
    }
}
