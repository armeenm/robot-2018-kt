package frc.team4096.engine.motion

import frc.team4096.engine.motion.util.PIDVAVals
import frc.team4096.engine.motion.util.PVAJData
import frc.team4096.robot.misc.MiscConsts
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

/**
 * Custom asynchronous PID(F) controller.
 * Proportional, integral, and derivative gains, as well as fixed feed-forward.
 *
 * @param pidfVals P, I, D, and F values
 * @param profile Profile to follow
 * @constructor Constructor
 */
class PIDVAController(
	val pidfVals: PIDVAVals,
	val profile: MotionProfile,
	val freq: Double = 50.0
) {
	var isEnabled = false
	private var error = 0.0
	private var lastError = 0.0
	private var integral = 0.0
	private var derivative = 0.0
	private var encoderPos = profile.source()
	private var pvajData = profile.follow(encoderPos)
	private val dt = 1 / freq

	/**
	 * Enables controller and launches coroutine.
	 */
	fun enable() {
		isEnabled = true
		launch {
			while (isEnabled) {
				encoderPos = profile.source()
				pvajData = profile.follow(encoderPos)
				error = pvajData.pos - encoderPos
				integral += error * dt
				derivative = (error - lastError) / dt

				profile.sink(
					pidfVals.kP * error +
						pidfVals.kI * integral +
						pidfVals.kD * derivative +
						pidfVals.kV * pvajData.vel +
						pidfVals.kA * pvajData.accel
				)

				lastError = error

				// Wait 20ms (50Hz)
				delay((dt * 1000).toInt())
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
		pvajData = PVAJData()
	}
}