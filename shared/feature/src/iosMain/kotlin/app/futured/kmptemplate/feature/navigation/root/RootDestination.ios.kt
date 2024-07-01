package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.navigation.signedin.SignedInDestination

actual object RootDestinationDefaults {
    /**
     * Returns initial stack for [RootDestination.SignedIn] destination.
     *
     * On Android this can be a single destination, others will be created lazily as user taps on bottom navigation items.
     *
     * On iOS, this must be a complete list of all possible bottom navigation tabs,
     * last being the one on top of the stack = default selected.
     */
    actual fun getInitialSignedInStack(): List<SignedInDestination> {
        return listOf(
            SignedInDestination.C,
            SignedInDestination.B(),
            SignedInDestination.A,
        )
    }
}
