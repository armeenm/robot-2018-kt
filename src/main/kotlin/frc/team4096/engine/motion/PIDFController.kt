package frc.team4096.engine.motion

import edu.wpi.first.wpilibj.PIDSourceType
import frc.team4096.engine.motion.util.PIDFVals
import frc.team4096.robot.misc.MiscConsts
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

/**
 * Custom asynchronous PID(F) controller.
 * Proportional, integral, and derivative gains, as well as fixed feed-forward.
 *
 * @param pidfVals P, I, D, and F values
 * @param setpoint Target setpoint
 * @param pidSourceFun Source lambda (e.g. encoder get)
 * @param pidSinkFun Sink lambda (e.g. motor controller set)
 * @constructor Constructor
 */
class PIDFController(
	val pidfVals: PIDFVals,
	var setpoint: Double,
	val pidSourceFun: () -> Double,
	val pidSinkFun: (Double) -> Unit,
	val pidSourceType: PIDSourceType
) {
	var isEnabled = false
	var error = 0.0
	var lastError = 0.0
	var integral = 0.0
	var derivative = 0.0

	/**
	 * Enables controller and launches coroutine.
	 */
	fun enable() {
		isEnabled = true
		launch {
			while (isEnabled) {
				error = setpoint - pidSourceFun()
				integral += (error * MiscConsts.K_DT)
				derivative = (error - lastError) / MiscConsts.K_DT

				pidSinkFun(
					pidfVals.kP * error +
						pidfVals.kI * integral +
						pidfVals.kD * derivative +
						pidfVals.kF
				)

				lastError = error

				// Wait 20ms (50Hz)
				delay((MiscConsts.K_DT * 1000).toInt())
			}
		}
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