package app.futured.kmptemplate.feature_v3.navigation.root

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

enum class NavigationTab(
    val title: StringDesc
) {
    HOME("Home".desc()),
    PROFILE("Profile".desc())
}
