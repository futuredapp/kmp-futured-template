package app.futured.kmptemplate.feature_v3.navigation.root

actual object RootNavHostDefaults {
    /**
     * Returns initial stack for bottom navigation.
     *
     * On Android this can be a single destination, others will be created lazily as user taps on bottom navigation items.
     *
     * On iOS, this must be a complete list of all possible bottom navigation tabs,
     * last being the one on top of the stack = default selected.
     */
    actual fun getInitialStack(): List<RootConfig> = listOf(RootConfig.Profile(), RootConfig.Home())
}
