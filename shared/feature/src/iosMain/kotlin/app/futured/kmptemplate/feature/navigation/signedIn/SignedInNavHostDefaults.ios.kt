package app.futured.kmptemplate.feature.navigation.signedIn

actual object SignedInNavHostDefaults {
    /**
     * Returns initial stack for bottom navigation.
     *
     * On Android this can be a single destination, others will be created lazily as user taps on bottom navigation items.
     *
     * On iOS, this must be a complete list of all possible bottom navigation tabs,
     * last being the one on top of the stack = default selected.
     */
    actual fun getInitialStack(
        initialConfig: SignedInConfig,
    ): List<SignedInConfig> = when (initialConfig) {
        is SignedInConfig.Home -> listOf(SignedInConfig.Profile(), initialConfig)
        is SignedInConfig.Profile -> listOf(SignedInConfig.Home(), initialConfig)
    }
}
