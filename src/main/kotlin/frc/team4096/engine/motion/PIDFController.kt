package frc.team4096.engine.motion

import frc.team4096.engine.async.AsyncLooper
import frc.team4096.engine.motion.util.PIDVAVals
import frc.team4096.robot.misc.MiscConsts
import kotlin.math.abs

/**
 * Custom asynchronous PID(F) controller.
 * Proportional, integral, and derivative gains, as well as fixed feed-forward.
 *
 * @param pidfVals P, I, D, and F values
 * @param source Source lambda (e.g. encoder get)
 * @param sink Sink lambda (e.g. motor controller set)
 * @constructor Constructor
 */
class PIDFController(
	val pidfVals: PIDVAVals,
	val source: () -> Double,
	val sink: (Double) -> Unit,
	val quitOnTarget: Boolean = true,
	val freq: Double = 100.0
) {
	private var error = 0.0
	private var lastError = 0.0
	private var integral = 0.0
	private var derivative = 0.0

	private var sensorPos = 0.0
	private var output = 0.0

	private var dt = 1 / freq

	private val looper = AsyncLooper(freq, true) { run() }
	var isEnabled
		get() = looper.job.isActive
		set(state) {
			looper.apply { if (state) start() else stop() }
		}

	var setpoint = 0.0
	var tolerance = 0.0

	val onTarget
		get() = (abs(error) <= abs(tolerance))

	/**
	 * Enables controller and launches coroutine.
	 */
	fun enable() { isEnabled = true }

	private fun calculate() {
		error = setpoint - sensorPos
		integral += error * MiscConsts.K_DT
		derivative = (error - lastError) / MiscConsts.K_DT

		output =
			pidfVals.kP * error +
			pidfVals.kI * integral +
			pidfVals.kD * derivative +
			pidfVals.kF

		lastError = error

	}

	private fun run() {
		if (onTarget && quitOnTarget) return

		sensorPos = source()
		calculate()
		sink(output)
	}

	/**
	 * Disables controller.
	 */
	fun disable() {
		isEnabled = false

		error = 0.0
		lastError = 0.0
		integral = 0.0
		derivative = 0.0
	}
}