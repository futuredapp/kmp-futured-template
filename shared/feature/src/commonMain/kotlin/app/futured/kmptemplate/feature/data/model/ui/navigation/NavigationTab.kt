package app.futured.kmptemplate.feature.data.model.ui.navigation

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

/**
 * A model for displaying bottom navigation tabs.
 */
enum class NavigationTab(
    val title: StringDesc,
) {
    A("Tab A".desc()),
    B("Tab B".desc()),
    C("Tab C".desc()),
}
