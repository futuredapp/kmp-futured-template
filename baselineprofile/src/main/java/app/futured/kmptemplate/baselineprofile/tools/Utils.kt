package app.futured.kmptemplate.baselineprofile.tools

import android.util.Log
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.SearchCondition
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import app.futured.kmptemplate.baselineprofile.tools.Constants.DEFAULT_TIMEOUT
import java.io.ByteArrayOutputStream

/**
 * Finds an element by [selector].
 * If not found, fails with [AssertionError] and dumps the window hierarchy.
 */
fun UiDevice.findOrFail(
    selector: BySelector,
    message: String? = null,
): UiObject2 {
    val element = findObject(selector)
    if (element == null) {
        val hierarchy = getWindowHierarchy()
        Log.d("Benchmark", hierarchy)
        throw AssertionError((message ?: "Object not on screen ($selector)") + "\n$hierarchy")
    }
    return element
}

/**
 * Waits until a [searchCondition] evaluates to true.
 * If not found within [timeout], fails with [AssertionError] and dumps the window hierarchy.
 * For example, wait [Until.hasObject] to wait until an element is present on screen.
 */
fun UiDevice.waitOrFail(
    searchCondition: SearchCondition<Boolean>,
    timeout: Long = DEFAULT_TIMEOUT,
    message: String? = null,
) {
    if (!wait(searchCondition, timeout)) {
        val hierarchy = getWindowHierarchy()
        Log.d("Benchmark", hierarchy)
        throw AssertionError((message ?: "Object not on screen") + "\n$hierarchy")
    }
}

fun UiDevice.waitOrNothing(
    selector: BySelector,
    timeout: Long = DEFAULT_TIMEOUT,
    message: String? = null,
): UiObject2? {
    if (wait(Until.hasObject(selector), timeout)) {
        return findOrFail(selector, message)
    }

    return null
}

/**
 * Combines waiting for an element and returning it.
 * If an object is not present, it throws [AssertionError] and dumps the window hierarchy.
 */
fun UiDevice.waitAndFind(
    selector: BySelector,
    timeout: Long = DEFAULT_TIMEOUT,
    message: String? = null,
): UiObject2 {
    waitOrFail(Until.hasObject(selector), timeout, message)
    return findOrFail(selector, message)
}

/**
 * Simplifies dumping window hierarchy
 */
fun UiDevice.getWindowHierarchy(): String {
    val output = ByteArrayOutputStream()
    dumpWindowHierarchy(output)
    return output.toString()
}
