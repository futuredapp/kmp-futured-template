package app.futured.kmptemplate.platform.binding

/**
 * Provides all information about application and current application build.
 */
interface Platform {

    /**
     * Application name.
     */
    val appName: String

    /**
     * Application version in semver format, eg. 1.2.4, should default to "Unknown" if not able to retrieve.
     */
    val appVersionName: String

    /**
     * Current build number.
     */
    val appBuildNumber: String

    /**
     * Get applicationId / bundleId.
     */
    val applicationId: String

    /**
     * Get name of the OS with version, eg. "Android 13" or "iOS 16.2".
     */
    val osNameWithVersion: String

    /**
     * Get device model eg. Pixel 5, or iPhone 15 Pro, etc.
     */
    val deviceModel: String
}
