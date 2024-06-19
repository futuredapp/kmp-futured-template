package app.futured.kmptemplate.benchmark

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import app.futured.kmptemplate.benchmark.flows.startup
import app.futured.kmptemplate.benchmark.tools.Constants.ITERATION_COUNT
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This test measures speed of app startup with and without Baseline Profiles.
 * Must be ran on a real device, it won't work in emulator.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class StartupBenchmarks {

    @get:Rule
    val rule = MacrobenchmarkRule()

    @Test
    fun startupCompilationNone() =
        benchmark(CompilationMode.None())

    @Test
    fun startupCompilationBaselineProfiles() =
        benchmark(CompilationMode.Partial(BaselineProfileMode.Require))

    private fun benchmark(compilationMode: CompilationMode) {
        // The application id for the running build variant is read from the instrumentation arguments.
        rule.measureRepeated(
            packageName = InstrumentationRegistry.getArguments().getString("targetAppId")
                ?: throw Exception("targetAppId not passed as instrumentation runner arg"),
            metrics = listOf(StartupTimingMetric()),
            compilationMode = compilationMode,
            startupMode = StartupMode.COLD,
            iterations = ITERATION_COUNT,
            setupBlock = {
                pressHome()
            },
            measureBlock = {
                startup()
            },
        )
    }
}
