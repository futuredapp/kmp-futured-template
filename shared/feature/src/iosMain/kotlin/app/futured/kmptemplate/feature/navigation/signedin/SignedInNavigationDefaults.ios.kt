package app.futured.kmptemplate.feature.navigation.signedin

import app.futured.kmptemplate.feature.navigation.root.RootDestination

actual object SignedInNavigationDefaults {

    /**
     * Returns initial stack for [RootDestination.SignedIn] destination.
     *
     * On Android this can be a single destination, others will be created lazily as user taps on bottom navigation items.
     *
     * On iOS, this must be a complete list of all possible bottom navigation tabs,
     * last being the one on top of the stack = default selected.
     */
    actual fun getInitialStack(): List<SignedInDestination> = listOf(
        SignedInDestination.C,
        SignedInDestination.B(),
        SignedInDestination.A,
    )
}
