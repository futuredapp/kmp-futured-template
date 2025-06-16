package app.futured.kmptemplate.resources.datetime

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.datetime.Instant

/**
 * Creates a [StringDesc] that formats the given [instant] using the provided [pattern].
 * The formatting will be localized according to the user's locale.
 *
 * This is a factory function for [DateTimeStringDesc].
 *
 * @param instant The [Instant] to be formatted.
 * @param pattern The pattern string to be used for formatting, following
 *                [UTS#35 Unicode Locale Data Markup Language (LDML)](http://www.unicode.org/reports/tr35/)
 *                syntax.
 * @return A [StringDesc] instance that will produce the formatted date-time string.
 *
 * @see DateTimeStringDesc
 */
@Suppress("FunctionName")
fun StringDesc.Companion.DateTime(instant: Instant, pattern: String): StringDesc = DateTimeStringDesc(instant, pattern)

/**
 * Creates a [StringDesc] which translates provided [Instant] into localized date-time string.
 *
 * This is an extension function for [Instant] that serves as a convenience wrapper around
 * [StringDesc.Companion.DateTime].
 *
 * @param pattern The pattern describing the date and time format, following the
 * [UTS#35 Unicode Locale Data Markup Language (LDML)](http://www.unicode.org/reports/tr35/)
 * standard.
 * @return A [StringDesc] instance that will format the [Instant] according to the specified
 * pattern and the user's current locale.
 *
 * For example, template "MMMMd" produces:
 * - "December 31" for "en_US" locale
 * - "31 December" for "en_GB" locale
 *
 * See [Android docs](https://developer.android.com/reference/android/icu/text/SimpleDateFormat)
 * See [iOS docs](https://developer.apple.com/documentation/foundation/dateformatter)
 */
fun Instant.desc(pattern: String) = StringDesc.Companion.DateTime(this, pattern)

/**
 * A [StringDesc] implementation which translates provided [Instant] into localized date-time string.
 *
 * Formats provided [Instant] using pattern in [UTS#35 Unicode Locale Data Markup Language (LDML)](http://www.unicode.org/reports/tr35/)
 * localized according to user's locale.
 *
 * For example, template "MMMMd" produces:
 * - "December 31" for "en_US" locale
 * - "31 December" for "en_GB" locale
 *
 * See [Android docs](https://developer.android.com/reference/android/icu/text/SimpleDateFormat)
 * See [iOS docs](https://developer.apple.com/documentation/foundation/dateformatter)
 */
expect class DateTimeStringDesc(instant: Instant, pattern: String) : StringDesc
