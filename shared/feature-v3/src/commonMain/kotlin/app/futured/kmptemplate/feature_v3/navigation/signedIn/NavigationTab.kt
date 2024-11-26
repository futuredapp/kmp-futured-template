package app.futured.kmptemplate.feature_v3.navigation.signedIn

import app.futured.kmptemplate.resources.MR
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc

enum class NavigationTab(
    val title: StringDesc,
) {
    HOME(MR.strings.tab_home.desc()),
    PROFILE(MR.strings.tab_profile.desc())
}
