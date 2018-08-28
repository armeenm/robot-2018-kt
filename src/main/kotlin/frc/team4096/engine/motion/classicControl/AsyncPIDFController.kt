package frc.team4096.engine.motion.classicControl

import frc.team4096.engine.async.AsyncLooper
import frc.team4096.engine.motion.PIDVAVals

/**
 * Custom asynchronous PID(F) controller.
 * Wrapper atop synchronous PIDFController with FF lambda.
 *
 * @param pidfVals P, I, D, and F values
 * @param feedforward Feedforward lambda
 * @param source Source lambda (e.g. encoder get)
 * @param sink Sink lambda (e.g. motor controller set)
 * @constructor Constructor
 */
class AsyncPIDFController(
        private val pidfVals: PIDVAVals,
        private val source: () -> Double,
        private val sink: (Double) -> Unit,
        private val feedforward: () -> Double = { 0.0 },
        private val quitOnTarget: Boolean = true,
        freq: Double = 100.0
) {
    val pidfController = PIDFController(pidfVals)

    private val looper = AsyncLooper(freq, true) { run() }
    var isEnabled
        get() = looper.job.isActive
        private set(state) {
            looper.apply { if (state) start() else stop() }
        }

    val onTarget
        get() = pidfController.onTarget

    /**
     * Enables controller and launches coroutine.
     */
    fun enable() {
        isEnabled = true
    }

    private fun run() {
        if (onTarget && quitOnTarget) return

        val sensorPos = source()
        val output = pidfController.calculate(sensorPos) + feedforward()
        sink(output)
    }

    /**
     * Disables controller.
     */
    fun disable() {
        isEnabled = false
        pidfController.reset()
    }
}