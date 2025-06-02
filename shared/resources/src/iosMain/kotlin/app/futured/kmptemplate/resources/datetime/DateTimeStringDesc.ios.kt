package app.futured.kmptemplate.resources.datetime

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.datetime.Instant
import kotlinx.datetime.toNSDate
import platform.Foundation.NSDateFormatter

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
actual class DateTimeStringDesc actual constructor(private val instant: Instant, private val pattern: String) : StringDesc {

    private val formatter by lazy { NSDateFormatter() }

    override fun localized(): String = formatter.run {
        setLocalizedDateFormatFromTemplate(pattern)
        stringFromDate(instant.toNSDate())
    }
}
