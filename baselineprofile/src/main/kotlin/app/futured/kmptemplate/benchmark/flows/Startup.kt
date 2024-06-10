package app.futured.kmptemplate.benchmark.flows

import androidx.benchmark.macro.MacrobenchmarkScope

/**
 * Starts the application and waits for the main activity to be displayed.
 */
fun MacrobenchmarkScope.startup() {
    startActivityAndWait()
}
