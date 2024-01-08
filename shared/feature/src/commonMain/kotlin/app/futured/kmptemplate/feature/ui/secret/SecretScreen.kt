package app.futured.kmptemplate.feature.ui.secret

import dev.icerock.moko.resources.desc.StringDesc

/**
 * This is a secret screen reachable only by deep link.
 */
interface SecretScreen {
    val title: StringDesc
    val text: StringDesc
    fun goBack()
}
