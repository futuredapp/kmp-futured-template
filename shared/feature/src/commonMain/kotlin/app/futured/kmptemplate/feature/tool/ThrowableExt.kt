package app.futured.kmptemplate.feature.tool

import app.futured.kmptemplate.network.rest.ext.isConnectionError
import app.futured.kmptemplate.network.rest.ext.isUnauthorizedError
import app.futured.kmptemplate.resources.MR
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

/**
 * Returns [StringDesc] describing this error's title for displaying in UIs that separate error UI into title and message.
 */
internal fun Throwable.uiTitle(): StringDesc = when {
    isConnectionError() -> StringDesc.Resource(MR.strings.error_network_title)
    isUnauthorizedError() -> StringDesc.Resource(MR.strings.error_unauthorized_title)
    else -> StringDesc.Resource(MR.strings.error_generic_title)
}

/**
 * Returns a [StringDesc] describing this error's subtitle for displaying in UIs that separate error UI into title and message.
 */
internal fun Throwable.uiMessage(): StringDesc = when {
    isConnectionError() -> StringDesc.Resource(MR.strings.error_network_description)
    isUnauthorizedError() -> StringDesc.Resource(MR.strings.error_unauthorized_description)
    else -> StringDesc.Resource(MR.strings.error_generic_description)
}

/**
 * Converts this [Throwable] into [UiError] UI model.
 */
internal fun Throwable.toUiError() = UiError(
    title = this.uiTitle(),
    message = this.uiMessage(),
)
