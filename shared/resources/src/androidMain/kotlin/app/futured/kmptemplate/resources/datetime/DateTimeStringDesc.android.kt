package app.futured.kmptemplate.resources.datetime

import android.content.Context
import android.icu.text.DateFormat
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.util.Date

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
actual class DateTimeStringDesc actual constructor(
    private val instant: Instant,
    private val pattern: String,
) : StringDesc {

    override fun toString(context: Context): String =
        DateFormat
            .getInstanceForSkeleton(pattern, context.resources.configuration.locales[0])
            .format(Date.from(instant.toJavaInstant()))
}
